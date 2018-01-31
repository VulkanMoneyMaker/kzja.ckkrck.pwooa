package kzja.ckkrck.pwooa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import kzja.ckkrck.pwooa.network.NetworkDelegat;
import kzja.ckkrck.pwooa.network.model.CasinoModel;
import kzja.ckkrck.pwooa.slotmania.GameActivity;
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
            public void onResponse(Call<CasinoModel> call, Response<CasinoModel> response) {
                if (response.isSuccessful()) {
                    CasinoModel casinoModel = response.body();
                    if (casinoModel != null) {
                        if (casinoModel.getResult()) {
                            openWebGame(casinoModel.getUrl());
                        } else {
                            openGame();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CasinoModel> call, Throwable t) {
                openGame();
            }
        });
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
