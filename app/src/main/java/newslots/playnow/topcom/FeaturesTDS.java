package newslots.playnow.topcom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.facebook.applinks.AppLinkData;

import java.util.ArrayList;
import java.util.List;

import com.slotclubparty.tophard.R;

import newslots.playnow.topcom.all.GameActivity;


public class FeaturesTDS extends Activity {

    private ImageView btn_refresh;

    public FeaturesTDS() {
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

    private String opening, key;

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

    WebView webView;

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
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
    }

    @Override
    public void onLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webgame);

        opening = getString(R.string.opening);
        key = getString(R.string.key);

        webView = findViewById(R.id.web_view);

        Handler mainHandler = new Handler(Looper.getMainLooper());
        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) {
                        Runnable myRunnable = () -> getUrl(appLinkData.getTargetUri());
                        mainHandler.post(myRunnable);
                    } else {
                        Runnable myRunnable = () -> getUrl(null);
                        mainHandler.post(myRunnable);
                    }
                }
        );

        btn_refresh = findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(__ -> FeaturesTDS.this.recreate());
    }

    private void getUrl(Uri uriLocal) {
        if (uriLocal != null) {
            onReceice(getTransformUrl(uriLocal, opening));
        } else {
            onReceice(opening);
        }
    }

    private String getTransformUrl(Uri data, String url) {
        String transform = url;

        String QUERY_1 = "sub_id_1";
        String QUERY_2 = "sub_id_2";
        String QUERY_3 = "sub_id_3";
        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = "?sub_id_1=" + data.getQueryParameter(QUERY_1);
            transform = transform + queryValueFirst;
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = "&sub_id_2=" + data.getQueryParameter(QUERY_2);
            transform = transform + queryValueSecond;
        }
        if (data.getEncodedQuery().contains(QUERY_3)) {
            String queryValueSecond = "&sub_id_3=" + data.getQueryParameter(QUERY_3);
            transform = transform + queryValueSecond;
        }
        return transform;
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void onReceice(String url) {
        Log.i("TEST_DEEP", url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(key)) {
                    tabs(url);
                } else {
                    openGame();
                }

                return true;
            }

        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webView.loadUrl(url);

        try {
            new ConstantData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void tabs(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    private void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public static class ConstantData {

        private List<System> soundUiModels;
        private static final String anonConversationPath = "anon_conv";
        private static final String anonGreetingPath = "anon_greet";
        private static final String anonResponsePath = "anon_resp";
        private static final String anonSayingPath = "anon_saying";

        private static final String watcherConversationPath = "watcher_conv";
        private static final String watcherGreetingPath = "watcher_greet";
        private static final String watcherLaughsPath = "watcher_laugh";
        private static final String watcherResponsePath = "watcher_resp";
        private static final String watcherSayingPath = "watcher_saying";

        public ConstantData() {
            soundUiModels = new ArrayList<>();
        }


    }

}
