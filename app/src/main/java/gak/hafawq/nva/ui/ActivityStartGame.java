package gak.hafawq.nva.ui;


import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.transitions.CCFadeTransition;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import gak.hafawq.nva.DataLayer.ForLogo;
import gak.hafawq.nva.DataLayer.TitleLayer;
import gak.hafawq.nva.Other.PlayManager;
import gak.hafawq.nva.utils.RandomUtil;



public class ActivityStartGame extends Activity implements BaseLogicView {
	private CCGLSurfaceView mGLSurfaceView;	
	private boolean startState;

	private long mGameId = -1;

	//@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setupWindowParams();

        mGLSurfaceView = new CCGLSurfaceView(this);        
        if(!startState){
			setupCCDirector();
			startState = true;
        }
	    
    }

	@Override
	public void createdGame(long gameId) {
		mGameId = gameId;
	}

	@Override
	public long getGameId() {
		return mGameId;
	}

	@Override
	public void dataUpdates(boolean success) {
		if (!success) {
			mGameId = -1;
		}
	}

	@Override
	public void needReload(boolean isNeed) {
		if (isNeed) {
			Intent intent = getIntent();
			if (intent != null) {
				mGameId = intent.getLongExtra("id", -1);
			}
		}
	}

	public void getAdmob(){
//
	}
	public void getInterstitialAd(){       	

	}
    public void getVungleAd(){

    }
    
    //@Override 
    public void onStart() {
        super.onStart();       

    }    
   
    @Override
	public void onBackPressed() {
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
    	    @Override
    	    public void onClick(DialogInterface dialog, int which) {
    	        switch (which){
    	        case DialogInterface.BUTTON_POSITIVE:
    	        	if(Resources.titleState){
    					Resources.titleState = false;
    					CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, TitleLayer.scene()));								
    				}else{
    					Resources.stopSound();
    					CCDirector.sharedDirector().end();
    			        PlayManager.releaseScoreManager();
    			        finish();
    				}	
    	            break;

    	        case DialogInterface.BUTTON_NEGATIVE:
    	            //No button clicked
    	            break;
    	        }
    	    }
    	};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure? You may lose all your coins.").setPositiveButton("Yes", dialogClickListener)
    	    .setNegativeButton("No", dialogClickListener).show();
	}
    
    
	private void InitParam() { 		
		Resources.g_Context = this;
		Resources.curLevel = 1;
		Resources.curLine = 1;
		Resources.maxline = 1;
		Resources.bet = 1;
		
	}	
	@Override public void onPause() {
	      super.onPause();
	      CCDirector.sharedDirector().pause();
	      Resources.pauseSound();
	        
	 }

	 @Override public void onResume() {
	     super.onResume();
	     CCDirector.sharedDirector().resume();
	     Resources.resumeSound();
	     review();
	  }

	  @Override public void onDestroy() {
	       super.onDestroy();
	       Resources.stopSound();
	       CCDirector.sharedDirector().end();
	       PlayManager.releaseScoreManager();
	  }
   
	
    private LayoutParams createLayoutParams() {
        final DisplayMetrics pDisplayMetrics = new DisplayMetrics();
		CCDirector.sharedDirector().getActivity().getWindowManager().getDefaultDisplay().getMetrics(pDisplayMetrics);

		final float mRatio = (float) Resources.DEFAULT_W / Resources.DEFAULT_H;
		final float realRatio = (float)pDisplayMetrics.widthPixels / pDisplayMetrics.heightPixels;

		final int width;
		final int height;
		if(realRatio < mRatio) {
			width = pDisplayMetrics.widthPixels;
			height = Math.round(width / mRatio);
		} else {
			height = pDisplayMetrics.heightPixels;
			width = Math.round(height * mRatio);
		}

		final LayoutParams layoutParams = new LayoutParams(width, height);

		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}




	
	/**
	 * Review
	 */
	private void review() {
		int random = RandomUtil.random.nextInt(100);
		if (random < 15) {
			ActivityStartGame.this.showReviewDialog();
		}
	}

    /**
     * Set Review Dialog
     */
    public void showReviewDialog() {
    
    }

    private void setupWindowParams() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
				LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
				LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void setupCCDirector() {
		CCDirector.sharedDirector().setScreenSize(CCDirector.sharedDirector().winSize().width,
				CCDirector.sharedDirector().winSize().height);
		CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		CCDirector.sharedDirector().getActivity().setContentView(mGLSurfaceView, createLayoutParams());
		CCDirector.sharedDirector().attachInView(mGLSurfaceView);
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);
		CCDirector.sharedDirector().setDisplayFPS(false);
		CCTexture2D.setDefaultAlphaPixelFormat(Config.ARGB_8888);

		InitParam();
		CCDirector.sharedDirector().runWithScene( ForLogo.scene());
	}




}