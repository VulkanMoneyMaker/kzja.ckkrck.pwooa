package lots.slotsazinotopora.triqaz;

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


import lots.slotsazinotopora.triqaz.all.GameActivity;


public class Tools extends Activity {

    private ImageView btn_refresh;

    public Tools() {
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
        btn_refresh.setOnClickListener(__ -> Tools.this.recreate());
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

        private List<SoundUiModel> soundUiModels;
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

        List<SoundUiModel> generateConstantData() {
            generateData(anonConversationPath, anConversations, Role.ANONYMOUS, Category.CONVERSATIONS);
            generateData(anonGreetingPath, anGreetings, Role.ANONYMOUS, Category.GREETINGS);
            generateData(anonResponsePath, anResponses, Role.ANONYMOUS, Category.RESPONSES);
            generateData(anonSayingPath, anSayings, Role.ANONYMOUS, Category.SAYINGS);
            generateData(watcherConversationPath, twConversations, Role.WATCHER, Category.CONVERSATIONS);
            generateData(watcherGreetingPath, twGreetings, Role.WATCHER, Category.GREETINGS);
            generateData(watcherLaughsPath, twLaughs, Role.WATCHER, Category.LAUGHS);
            generateData(watcherResponsePath, twResponses, Role.WATCHER, Category.RESPONSES);
            generateData(watcherSayingPath, twSayings, Role.WATCHER, Category.SAYINGS);
            return soundUiModels;
        }


        private void generateData(String basePath, String[] titles, Role role, Category category) {
            for (int i = 0; i < titles.length; i++) {
                String title = titles[i];
                String path = String.format("%s%s", basePath, i);
                soundUiModels.add(new SoundUiModel(title, path, false, role, category));
            }
        }


        private String[] anConversations = {
                "IF YOU CONTINUE WARNINGS WILL TURN INTO ACTIONS",
                "YOU HAVE MADE A COLOSSAL MISTAKE",
                "YOU WILL LISTEN AND YOU WILL LISTEN WELL",
                "YOUR THREATS ARE SIMPLY IRRELEVANT",
                "DO YOU UNDERSTAND",
                "IF YOU DO NOT COMPLETE THIS TASK",
                "IF YOU DO NOT LISTEN, WARNINGS WILL TURN INTO ACTIONS",
                "YOU MAY QUESTION OUR ABILITIES",
                "YOU WILL COMPLY",
                "YOU WILL LISTEN, FOR WHAT I HAVE TO TELL YOU, WILL NOT BE REPEATED",
                "YOU WILL REGRET, EVER, CROSSING ANONYMOUS",
                "YOU WILL NOT QUESTION US",
        };

        // ANONYMOUS - GREETINGS SOUNDS LIST
        private static String[] anGreetings = {
                "GOOD MORNING",
                "GREETINGS",
                "HELLO",
                "GOODBYE",
                "GOODNIGHT",
                "LIGHTS OUT",
        };


        // ANONYMOUS - RESPONSES SOUNDS LIST
        private static String[] anResponses = {
                "I AGREE",
                "I DISAGREE",
                "YES, YOU ARE CORRECT",
                "NO, YOU ARE INCORRECT",
                "YES",
                "NO",
        };

        private static String[] anSayings = {
                "EXPECT US",
                "I AM JUSTICE",
                "I AM THE JUSTICE, YOU TRIED TO RUN FROM",
                "I, AM, ANONYMOUS",
                "JUSTICE ANSWERS TO NO MAN",
                "OUR MEETING WAS NOT BY CHANCE",
                "SAY HI TO YOUR MOTHERBOARD FOR ME",
                "THIS IS MY GAME, AND IN THIS GAME, I WILL ALWAYS WIN",
                "WE ARE A NIGHTMARE, OF CALCULATED CHAOS, FROM WHICH YOU WILL NEVER AWAKEN",
                "WE ARE LEGION",
                "WE ARE THE EYES OF THE BLIND, AND THE VOICE OF THE SILENT",
                "WE DO NOT FORGIVE, WE DO NOT FORGET, EXPECT US",
                "WE WILL BLUR THE LINE, BETWEEN NIGHTMARE AND REALITY",
                "WE ARE ANONYMOUS",
                "YOU ARE ABOUT TO EXPERIENCE FEELINGS, YOU DID NOT KNOW EVEN EXISTED",
                "YOU HAVE BEEN TARGETED BY, ANONYMOUS",
                "YOU SHOULD HAVE EXPECTED US",
                "YOU WILL FIND NO MERCY HERE",
                "YOUR CROCODILE TEARS MEAN NOTHING TO US",
        };


        private static String[] twConversations = {
                "ARE YOU FEELING BRAVE ON THIS DAY",
                "CAN I TELL YOU A SECRET",
                "CHECK YOUR FRONT DOOR",
                "DO I SCARE YOU",
                "DO YOU HEAR ME",
                "DO YOU REALLY THINK YOUR PATHETIC THREATS WILL DETER US",
                "DO YOU REALLY WANT TO KNOW WHO I AM",
                "DO YOU THINK YOU SCARE ME",
                "I CAN SEE YOU",
                "I KNOW WHAT YOU DID",
                "I KNOW WHO YOU ARE",
                "IT IS TOO LATE FOR YOU NOW",
                "WE ARE EVERYTHING YOU ARE AFRAID OF",
                "WE ARE THE FRIENDS YOU NEVER KNEW YOU HAD",
                "WE HAVE BEEN WATCHING YOU FOR A LONG TIME",
                "WE KNOW EVERYTHING ABOUT YOU",
                "WE KNOW EVERYTHING YOU HAVE DONE",
                "WE WILL NO LONGER ASK POLITELY",
                "WHAT DO YOU FEAR THE MOST",
                "YOU DO NOT KNOW US BUT WE MOST CERTAINLY KNOW YOU",
                "YOU SHOULD BE QUIET",
                "YOU WANT TO KNOW WHO WE ARE",
                "YOUR QUESTIONS WILL NO LONGER BE RESPECTED WITH ANSWERS",
        };


        // THE WATCHER - GREETINGS SOUNDS LIST
        private static String[] twGreetings = {
                "FAREWELL",
                "GOODBYE",
                "GOODNIGHT V1",
                "GOODNIGHT V2",
                "HELLO V1",
                "HELLO V2",
                "HELLO THERE V1",
                "HELLO THERE V2",
        };

        // THE WATCHER - LAUGHS SOUNDS LIST
        private static String[] twLaughs = {
                "LONG LAUGH V1",
                "LONG LAUGH V2",
                "LONG LAUGH V3",
                "MEDIUM LAUGH V1",
                "MEDIUM LAUGH V2",
                "MEDIUM LAUGH V3",
                "SHORT LAUGH V1",
                "SHORT LAUGH V2",
                "SHORT LAUGH V3",
        };

        // THE WATCHER - RESPONSES SOUNDS LIST
        private static String[] twResponses = {
                "GO AHEAD",
                "GOOD V1",
                "GOOD V2",
                "IS THAT RIGHT",
                "MAYBE V1",
                "MAYBE V2",
                "NEVER",
                "NO V1",
                "NO V2",
                "VERY WELL",
                "YES V1",
                "YES V2",
        };


        // THE WATCHER - RESPONSES SOUNDS LIST
        private static String[] twSayings = {
                "BLURRING THE LINE BETWEEN NIGHTMARE AND REALITY IS OUR SPECIALTY",
                "HOW UNLUCKY YOU ARE TO HAVE 2 FACES, AND BOTH OF THEM ARE TRULY UGLY",
                "I AM THE ARCHITECT OF FEAR",
                "I AM THE MONSTER THAT HIDES UNDER YOUR BED",
                "I AM THE NIGHTMARE YOU'LL NEVER WAKE FROM",
                "I AM THE NOISES YOU HEAR IN THE NIGHT",
                "NO ONE NOTICES IF SOMEONE DISAPPEARS, IF THEY ARE ALREADY IN THE DARKNESS",
                "OH HOW PATHETIC, YOU DARE BEG FOR THE JUSTICE YOU DENIED OTHERS",
                "SAY HI TO YOUR MOTHER FOR ME",
                "WE ARE CONTRACTED MERCENARIES",
                "WE ARE HERE FOR REDEMPTION, AND REDEMPTION ALONE",
                "WE ARE THE MOTHS DRAWN TO THE BURNING FLAME ON VILE INDISCRETIONS",
                "WE ARE YOUR CONSEQUENCE",
                "WE ARE YOUR WATCHERS",
                "WE OPERATE IN DARKNESS AND THE AUTHORITIES CAN'T STOP WHAT THEY CAN'T SEE",
                "WELCOME TO YOUR WORST NIGHTMARE",
                "YOU BETTER MAKE SURE YOUR DOORS ARE LOCKED TONIGHT",
        };


    }



}
