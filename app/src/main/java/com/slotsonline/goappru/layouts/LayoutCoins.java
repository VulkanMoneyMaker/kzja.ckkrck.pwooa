package com.slotsonline.goappru.layouts;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import com.slotsonline.goappru.models.Buttons;

import com.slotsonline.goappru.activities.Data;

public class LayoutCoins extends CCLayer {
	public int coinCount = 0;
	
	private static long lastTime = 0;



	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutCoins());
		return scene;
	}

	/*****************************************************************************************************************************************************************************************************************/
	public LayoutCoins() {
		super();
		schedule("getInfo", 1.0f / 10.0f);
	}

	public void getInfo(float dt) {
		unschedule("getInfo");
		CCSprite img_back = CCSprite.sprite(Data._getImg("setting/coinSetting"));
		Data.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		Buttons buyBtn = Buttons.button(Data._getImg("setting/buyBtn"),
				Data._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(Data._getX(717), Data._getY(320));
		addChild(buyBtn);

		Buttons backBtn = Buttons.button(Data._getImg("setting/PlusBack"),
				Data._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(Data._getX(900), Data._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {



	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		Data.playEffect(Data.click);
		if (Data.GAME_STATE.equals("title")) {
			Data.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, LayoutTitles.scene()));
		} else if (Data.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, LayoutGames.scene()));
		}
	}

}
