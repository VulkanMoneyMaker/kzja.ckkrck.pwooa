package gjre.tjewqd.dsaj.Layer;

///import org.cocos2d.nodes.CCDirector;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import gjre.tjewqd.dsaj.Other.GrowButton;

import gjre.tjewqd.dsaj.slotmania.G;

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
		CCSprite img_back = CCSprite.sprite(G._getImg("setting/coinSetting"));
		G.setScale(img_back);
		img_back.setAnchorPoint(0, 0);
		img_back.setPosition(0, 0);
		addChild(img_back);

		GrowButton buyBtn = GrowButton.button(G._getImg("setting/buyBtn"),
				G._getImg("setting/buyBtn"), this, "coinBuy", 0);

		buyBtn.setPosition(G._getX(717), G._getY(320));
		addChild(buyBtn);

		GrowButton backBtn = GrowButton.button(G._getImg("setting/PlusBack"),
				G._getImg("setting/PlusBack"), this, "backLayer", 0);
		// backBtn.setColor(new ccColor3b(0,0,0));
		backBtn.setPosition(G._getX(900), G._getY(50));
		addChild(backBtn);
	}

	/*****************************************************************************************************************************************************************************************************************/
	public void coinBuy(Object sender) {



	}
	
	/*****************************************************************************************************************************************************************************************************************/
	public void backLayer(Object sender) {
		G.playEffect(G.click);
		if (G.GAME_STATE.equals("title")) {
			G.titleState = false;
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		} else if (G.GAME_STATE.equals("game")) {
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(0.7f, GameLayer.scene()));
		}
	}

}
