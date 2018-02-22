package gak.hafawq.nva.DataLayer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import gak.hafawq.nva.Other.NextGameButton;

import gak.hafawq.nva.ui.Resources;

public class ItemForBuy extends CCLayer {
	public int coinCount = 0;
	
	private static long lastTime = 0;



	public static CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(new ItemForBuy());
		return scene;
	}

	/*****************************************************************************************************************************************************************************************************************/
	public ItemForBuy() {
		super();
		schedule("getInfo", 1.0f / 10.0f);
	}

	public void getInfo(float dt) {
		unschedule("getInfo");
		CCSprite img_back = CCSprite.sprite(Resources._getImg("setting/coinSetting"));
		Resources.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		NextGameButton buyBtn = NextGameButton.button(Resources._getImg("setting/buyBtn"),
				Resources._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(Resources._getX(717), Resources._getY(320));
		addChild(buyBtn);

		NextGameButton backBtn = NextGameButton.button(Resources._getImg("setting/PlusBack"),
				Resources._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(Resources._getX(900), Resources._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {



	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		Resources.playEffect(Resources.click);
		if (Resources.GAME_STATE.equals("title")) {
			Resources.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		} else if (Resources.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
