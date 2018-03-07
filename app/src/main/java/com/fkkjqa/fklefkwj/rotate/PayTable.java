package com.fkkjqa.fklefkwj.rotate;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.fkkjqa.fklefkwj.gambit.GrowButton;
import com.fkkjqa.fklefkwj.kektus.Total;

public class PayTable extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new PayTable());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public PayTable()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(Total._getImg(String.format("backImages/pay_table%d-hd", Total.curLevel)));
		Total.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		GrowButton retu =GrowButton.button(Total._getImg("Buttons/return"), Total._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(Total._getX(889), Total._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		Total.playEffect(Total.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}