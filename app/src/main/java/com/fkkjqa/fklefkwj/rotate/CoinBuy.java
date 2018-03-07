package com.fkkjqa.fklefkwj.rotate;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import com.fkkjqa.fklefkwj.gambit.GrowButton;

import com.fkkjqa.fklefkwj.kektus.Total;

public class CoinBuy extends CCLayer {
	public int coinCount = 0;
	
	private static long lastTime = 0;



	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new CoinBuy());
		return scene;
	}

	/*****************************************************************************************************************************************************************************************************************/
	public CoinBuy() {
		super();
		schedule("getInfo", 1.0f / 10.0f);
	}

	public void getInfo(float dt) {
		unschedule("getInfo");
		CCSprite img_back = CCSprite.sprite(Total._getImg("setting/coinSetting"));
		Total.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		GrowButton buyBtn = GrowButton.button(Total._getImg("setting/buyBtn"),
				Total._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(Total._getX(717), Total._getY(320));
		addChild(buyBtn);

		GrowButton backBtn = GrowButton.button(Total._getImg("setting/PlusBack"),
				Total._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(Total._getX(900), Total._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {



	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		Total.playEffect(Total.click);
		if (Total.GAME_STATE.equals("title")) {
			Total.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		} else if (Total.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
