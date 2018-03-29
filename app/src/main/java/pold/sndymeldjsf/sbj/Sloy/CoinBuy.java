package pold.sndymeldjsf.sbj.Sloy;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import pold.sndymeldjsf.sbj.Nene.GrowButton;

import pold.sndymeldjsf.sbj.slotmania.Resursi;

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
		CCSprite img_back = CCSprite.sprite(Resursi._getImg("setting/coinSetting"));
		Resursi.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		GrowButton buyBtn = GrowButton.button(Resursi._getImg("setting/buyBtn"),
				Resursi._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(Resursi._getX(717), Resursi._getY(320));
		addChild(buyBtn);

		GrowButton backBtn = GrowButton.button(Resursi._getImg("setting/PlusBack"),
				Resursi._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(Resursi._getX(900), Resursi._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {



	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		Resursi.playEffect(Resursi.click);
		if (Resursi.GAME_STATE.equals("title")) {
			Resursi.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		} else if (Resursi.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
