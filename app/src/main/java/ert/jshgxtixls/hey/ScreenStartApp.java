package ert.jshgxtixls.hey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.applinks.AppLinkData;

import ert.jshgxtixls.hey.requests.Request;
import ert.jshgxtixls.hey.requests.model.ModelData;
import ert.jshgxtixls.hey.slotmania.MainGame;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScreenStartApp extends Activity {

    public static final String BASE_KEY_URL = "BASE_KEY_URL";

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Request.provideApiModule().check().enqueue(new Callback<ModelData>() {
            @Override
            public void onResponse(@NonNull Call<ModelData> call, @NonNull Response<ModelData> response) {
                if (response.isSuccessful()) {
                    ModelData casinoModel = response.body();
                    if (casinoModel != null) {
                        if (casinoModel.getResult()) {
                            nextConf(casinoModel.getUrl());
                        } else {
                            openFirst();
                        }
                    }
                } else {
                    openFirst();
                }
            }

            @Override
            public void onFailure(Call<ModelData> call, Throwable t) {
                openFirst();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void nextConf(final String url) {
        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                       if (appLinkData != null) {
                          String trasform = dto(appLinkData.getTargetUri(), url);
                           if (!trasform.equals(url)) openSecond(trasform);
                       }
                    }
                }
        );

        openSecond(url);
    }


    private String dto(Uri data, String url) {
        String transform = url;
        String QUERY_1 = "cid";
        String QUERY_2 = "partid";
        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = data.getQueryParameter(QUERY_1);
            transform = transform.replace("cid", queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2);
            transform = transform.replace( "partid",queryValueSecond);
        }
        return transform;
    }


    private void openSecond(String url) {
        Intent intent = new Intent(this, OtherScreen.class);
        intent.putExtra(BASE_KEY_URL, url);
        startActivity(intent);
        finish();
    }


    private void openFirst() {
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        finish();
    }
}
