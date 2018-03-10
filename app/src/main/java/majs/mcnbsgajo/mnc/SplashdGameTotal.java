package majs.mcnbsgajo.mnc;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.applinks.AppLinkData;

import majs.mcnbsgajo.mnc.network.NetworkDelegat;
import majs.mcnbsgajo.mnc.network.model.CasinoModel;
import majs.mcnbsgajo.mnc.slotmania.GameActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashdGameTotal extends Activity {

    public static final String BASE_KEY_URL = "BASE_KEY_URL";

    public SplashdGameTotal() {
        super();
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
    }

    @Override
    public WindowManager getWindowManager() {
        return super.getWindowManager();
    }

    @Override
    public Window getWindow() {
        return super.getWindow();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    @Nullable
    @Override
    public View getCurrentFocus() {
        return super.getCurrentFocus();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean isVoiceInteraction() {
        return super.isVoiceInteraction();
    }

    @Override
    public boolean isVoiceInteractionRoot() {
        return super.isVoiceInteractionRoot();
    }

    @Override
    public VoiceInteractor getVoiceInteractor() {
        return super.getVoiceInteractor();
    }

    @Override
    public boolean isLocalVoiceInteractionSupported() {
        return super.isLocalVoiceInteractionSupported();
    }

    @Override
    public void startLocalVoiceInteraction(Bundle privateOptions) {
        super.startLocalVoiceInteraction(privateOptions);
    }

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

    @Override
    public void onLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped();
    }

    @Override
    public void stopLocalVoiceInteraction() {
        super.stopLocalVoiceInteraction();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    @Nullable
    @Override
    public CharSequence onCreateDescription() {
        return super.onCreateDescription();
    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
    }

    @Override
    public void onProvideAssistContent(AssistContent outContent) {
        super.onProvideAssistContent(outContent);
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
            transform = transform.replace("cid", queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2);
            transform = transform.replace( "partid",queryValueSecond);
        }
        return transform;
    }

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }

    @Override
    public boolean showAssist(Bundle args) {
        return super.showAssist(args);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void reportFullyDrawn() {
        super.reportFullyDrawn();
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
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
