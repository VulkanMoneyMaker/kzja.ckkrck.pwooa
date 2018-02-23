package com.slotsonline.goappru.layouts;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.slotsonline.goappru.models.Buttons;
import com.slotsonline.goappru.activities.Data;

public class LayoutTables extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutTables());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public LayoutTables()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(Data._getImg(String.format("backImages/pay_table%d-hd", Data.curLevel)));
		Data.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		Buttons retu = Buttons.button(Data._getImg("Buttons/return"), Data._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(Data._getX(889), Data._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		Data.playEffect(Data.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutGames.scene()));
	}
}