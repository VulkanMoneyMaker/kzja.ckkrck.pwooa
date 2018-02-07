package kzja.ckkrck.pwooa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import java.util.Date;

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
        if (isNoPlaytime() && checkNewOlders()) {
            openWebGame(null);
        } else {
            openGame();
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
}
