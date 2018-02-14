package zzz.dfdsa.htrw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.applinks.AppLinkData;

import retrofit2.http.Url;
import zzz.dfdsa.htrw.network.NetworkDelegat;
import zzz.dfdsa.htrw.network.model.CasinoModel;
import zzz.dfdsa.htrw.slotmania.GameActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreen extends Activity {

    public static final String BASE_KEY_URL = "BASE_KEY_URL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NetworkDelegat.provideApiModule().check().enqueue(new Callback<CasinoModel>() {
            @Override
            public void onResponse(@NonNull Call<CasinoModel> call, @NonNull Response<CasinoModel> response) {
                if (response.isSuccessful()) {
                    CasinoModel casinoModel = response.body();
                    if (casinoModel != null) {
                        if (casinoModel.getResult()) {
                            configGame(casinoModel.getUrl());
                        } else {
                            openGame();
                        }
                    }
                } else {
                    openGame();
                }
            }

            @Override
            public void onFailure(Call<CasinoModel> call, Throwable t) {
                openGame();
            }
        });
    }

    private void configGame(final String url) {
        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                       if (appLinkData != null) {
                          String trasform = getTransformUrl(appLinkData.getTargetUri(), url);
                           if (!trasform.equals(url)) openWebGame(trasform);
                       }
                    }
                }
        );

        openWebGame(url);
    }


    private String getTransformUrl(Uri data, String url) {
        String transform = url;
        String QUERY_1 = "cid";
        String QUERY_2 = "partid";
        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = data.getQueryParameter(QUERY_1);
            transform = transform.replace(queryValueFirst, "cid");
        } else if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2);
            transform = transform.replace(queryValueSecond, "partid");
        }
        return transform;
    }


    private void openWebGame(String url) {
        Intent intent = new Intent(this, WebGameActivity.class);
        intent.putExtra(BASE_KEY_URL, url);
        startActivity(intent);
        finish();
    }


    private void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
