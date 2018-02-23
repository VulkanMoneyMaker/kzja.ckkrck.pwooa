package com.slotsonline.goappru.activities;

import android.os.Bundle;

import com.slotsonline.goappru.models.Scores;

import org.cocos2d.nodes.CCDirector;

public class PresenterActivity {

    private ViewActivity viewActivity;

    public PresenterActivity(ViewActivity viewActivity) {
        this.viewActivity = viewActivity;
    }

    public void onCreate(Bundle saveInstanceState) {

        viewActivity.initLayers();
    }

    public void onPause() {
        CCDirector.sharedDirector().pause();
        Data.pauseSound();
    }

    public void onResume() {
        CCDirector.sharedDirector().resume();
        Data.resumeSound();
    }

    public void onDestroy() {
        Data.stopSound();
        CCDirector.sharedDirector().end();
        Scores.releaseScoreManager();
    }
}
