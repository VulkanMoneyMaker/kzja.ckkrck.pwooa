package com.slotclubparty.tophard;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;


public class AFApplication extends Application {
    private static final String TAG = AFApplication.class.getSimpleName();
    private static final String AF_DEV_KEY = "scA85DtfhxLRsw6NpixhEF";
    private AppsFlyerConversionListener conversionDataListener;
    @Override
    public void onCreate() {
        super.onCreate();
        conversionDataListener =
                new AppsFlyerConversionListener() {

                    @Override
                    public void onInstallConversionDataLoaded(Map<String, String> map) {
                        Log.i(TAG, "onInstallConversionDataLoaded");
                    }

                    @Override
                    public void onInstallConversionFailure(String s) {
                        Log.i(TAG, "onInstallConversionFailure");
                    }

                    @Override
                    public void onAppOpenAttribution(Map<String, String> map) {
                        Log.i(TAG, "onAppOpenAttribution");
                    }

                    @Override
                    public void onAttributionFailure(String s) {
                        Log.i(TAG, "onAttributionFailure");
                    }
                };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionDataListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
    }
}
