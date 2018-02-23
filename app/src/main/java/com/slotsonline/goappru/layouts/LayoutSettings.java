package com.slotsonline.goappru.layouts;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.slotsonline.goappru.models.Buttons;
import com.slotsonline.goappru.activities.Data;


public class LayoutSettings extends CCLayer
{
	Buttons on1;
	Buttons off1;
	Buttons on2;
	Buttons off2;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutSettings());
		return scene;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public LayoutSettings()
	{
		super();
		CCSprite im_back = CCSprite.sprite(Data._getImg("setting/setting"));
		Data.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = Buttons.button(Data._getImg("setting/onBtn"), Data._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= Buttons.button(Data._getImg("setting/off"), Data._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(Data._getX(768), Data._getY(332));
		off1.setPosition(Data._getX(768), Data._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = Buttons.button(Data._getImg("setting/onBtn"), Data._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= Buttons.button(Data._getImg("setting/off"), Data._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(Data._getX(768), Data._getY(194));
		off2.setPosition(Data._getX(768), Data._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		Buttons back = Buttons.button(Data._getImg("setting/backBtn"), Data._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(Data._getX(877), Data._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(Data.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(Data.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(Data.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			Data.bgmState = false;
			Data.pauseSound();
			Data.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			Data.bgmState = true;
			if(Data.stopSound){
				Data.resumeSound();
				Data.stopSound = false;
			}else{
				Data.playSound();
			}
		}
		Data.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(Data.effectState){
			Data.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			Data.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		Data.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		Data.playEffect(Data.click);
		Data.titleState = false;
		if(Data.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutTitles.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutGames.scene()));
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		Data.playEffect(Data.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		Data.playEffect(Data.click);
		getStateBgm();
	}
}