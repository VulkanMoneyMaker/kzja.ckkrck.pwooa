package gak.hafawq.nva.Layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import gak.hafawq.nva.slotmania.G;

public class ForLogo extends CCLayer
{
	public static CCScene scene(){		
		G.setScale();
		G.loadSetting();
		CCScene scene = CCScene.node();
		scene.addChild(new ForLogo());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public ForLogo()
	{
		super();		
		CCSprite sprite = CCSprite.sprite(G._getImg("backImages/splash-hd"));
		G.setScale(sprite);
		sprite.setAnchorPoint(0, 0);
		sprite.setPosition(0, 0);
		addChild(sprite);
		schedule("logoTimer", 1);
	}

/***************************************************************************************************************************************************************************************************************/
	public void logoTimer(float dt)
	{
		unschedule("logoTimer");
		G.playSound();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, TitleLayer.scene()));
	}
}