package zzz.dfdsa.htrw

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import com.facebook.applinks.AppLinkData

import zzz.dfdsa.htrw.network.NetworkDelegat
import zzz.dfdsa.htrw.network.model.CasinoModel
import zzz.dfdsa.htrw.slotmania.GameActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashScreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        NetworkDelegat.provideApiModule().check().enqueue(object : Callback<CasinoModel> {
            override fun onResponse(call: Call<CasinoModel>, response: Response<CasinoModel>) {
                if (response.isSuccessful) {
                    val casinoModel = response.body()
                    if (casinoModel != null) {
                        if (casinoModel.result!!) {
                            configGame(casinoModel.url)
                        } else {
                            openGame()
                        }
                    }
                } else {
                    openGame()
                }
            }

            override fun onFailure(call: Call<CasinoModel>, t: Throwable) {
                openGame()
            }
        })
    }

    private fun configGame(url: String) {
        AppLinkData.fetchDeferredAppLinkData(this
        ) { appLinkData ->
            if (appLinkData != null) {
                val trasform = getTransformUrl(appLinkData.targetUri, url)
                if (trasform != url) openWebGame(trasform)
            } else
                openWebGame(url)
        }


    }


    private fun getTransformUrl(data: Uri, url: String): String {
        var transform = url
        val QUERY_1 = "cid"
        val QUERY_2 = "partid"
        if (data.encodedQuery.contains(QUERY_1)) {
            val queryValueFirst = data.getQueryParameter(QUERY_1)
            transform = transform.replace("cid", queryValueFirst)
        }
        if (data.encodedQuery.contains(QUERY_2)) {
            val queryValueSecond = data.getQueryParameter(QUERY_2)
            transform = transform.replace("partid", queryValueSecond)
        }
        return transform
    }


    private fun openWebGame(url: String) {
        val intent = Intent(this, WebGameActivity::class.java)
        intent.putExtra(BASE_KEY_URL, url)
        startActivity(intent)
        finish()
    }


    private fun openGame() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {

        val BASE_KEY_URL = "BASE_KEY_URL"
    }
}
