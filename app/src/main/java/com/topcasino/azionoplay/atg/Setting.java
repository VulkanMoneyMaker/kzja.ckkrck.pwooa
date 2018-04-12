package com.topcasino.azionoplay.atg;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import com.topcasino.azionoplay.totaling.GrowButton;
import com.topcasino.azionoplay.mext.G;


public class Setting extends CCLayer
{
	GrowButton on1;
	GrowButton off1;
	GrowButton on2;
	GrowButton off2;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new Setting());
		return scene;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public Setting()
	{
		super();
		CCSprite im_back = CCSprite.sprite(G._getImg("setting/setting"));
		G.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = GrowButton.button(G._getImg("setting/onBtn"), G._getImg("setting/onBtn"),this,"setOnOff1",0);		
		off1= GrowButton.button(G._getImg("setting/off"), G._getImg("setting/off"),this,"setOnOff1",0);	
		on1.setPosition(G._getX(768),G._getY(332));
		off1.setPosition(G._getX(768),G._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = GrowButton.button(G._getImg("setting/onBtn"), G._getImg("setting/onBtn"),this,"setOnOff2",0);		
		off2= GrowButton.button(G._getImg("setting/off"), G._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(G._getX(768),G._getY(194));
		off2.setPosition(G._getX(768),G._getY(194));	
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		GrowButton back = GrowButton.button(G._getImg("setting/backBtn"), G._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(G._getX(877), G._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(G.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(G.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(G.bgmState){			
			on2.setVisible(false);
			off2.setVisible(true);
			G.bgmState = false;
			G.pauseSound();
			G.stopSound = true;			
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			G.bgmState = true;
			if(G.stopSound){
				G.resumeSound();
				G.stopSound = false;
			}else{
				G.playSound();					
			}
		}
		G.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(G.effectState){
			G.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			G.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		G.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		G.playEffect(G.click);
		G.titleState = false;
		if(G.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		G.playEffect(G.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		G.playEffect(G.click);
		getStateBgm();
	}
}