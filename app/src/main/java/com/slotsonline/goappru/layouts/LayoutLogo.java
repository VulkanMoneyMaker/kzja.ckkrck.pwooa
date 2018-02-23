package com.slotsonline.goappru.layouts;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;

import com.slotsonline.goappru.activities.Data;

public class LayoutLogo extends CCLayer
{
	public static CCScene scene(){		
		Data.setScale();
		Data.loadSetting();
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutLogo());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public LayoutLogo()
	{
		super();		
		CCSprite sprite = CCSprite.sprite(Data._getImg("backImages/splash-hd"));
		Data.setScale(sprite);
		sprite.setAnchorPoint(0, 0);
		sprite.setPosition(0, 0);
		addChild(sprite);
		schedule("logoTimer", 1);
	}

/***************************************************************************************************************************************************************************************************************/
	public void logoTimer(float dt)
	{
		unschedule("logoTimer");
		Data.playSound();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f, LayoutTitles.scene()));
	}
}