package com.onlineslots.jjahhz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.onlineslots.jjahhz.slotmania.GameActivity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class SplashScreen extends Activity {

    public static final String BASE_KEY_URL = "BASE_KEY_URL";
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       disposable = Observable.timer(2, TimeUnit.SECONDS).subscribe(t -> {
            if (isNoPlaytime() && checkNewOlders() && isOnline()) {
                openWebGame(null);
            } else {
                openGame();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private boolean isNoPlaytime() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            TimeZone tz = TimeZone.getDefault();
            Date now = new Date();
            int offsetFromUtc = tz.getOffset(now.getTime()) / 1000 / 3600;
            int[] timezone = {2,3,4,7,8,9,10,11,12};
            for (int item : timezone) {
                if (offsetFromUtc == item)
                    return true;
            }
        } else {
            return true;
        }

        return false;
    }

    private static final String COUNTY_ONE = "RU";
    private static final String COUNTRY_TWO = "ru";
    private static final String COUNTRY_THREE = "rus";

    private boolean checkNewOlders() {
        String typeOlderUsers = null;
        if (getSystemService(Context.TELEPHONY_SERVICE) != null)
            typeOlderUsers = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso();
        else
            return false;
        return typeOlderUsers != null && (typeOlderUsers.equalsIgnoreCase(COUNTRY_TWO) || typeOlderUsers.equalsIgnoreCase(COUNTRY_THREE));
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
