package com.fkkjqa.fklefkwj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import com.fkkjqa.fklefkwj.kektus.PageMainGame;
import io.reactivex.Observable;


public class NextFFDajo extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(value -> {
                    if (isOnline()) openGameOne();
                    else openGameSecond();
                });

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    private void openGameOne() {
        Intent intent = new Intent(this, GogaScreen.class);
        startActivity(intent);
        finish();
    }


    private void openGameSecond() {
        Intent intent = new Intent(this, PageMainGame.class);
        startActivity(intent);
        finish();
    }
}
