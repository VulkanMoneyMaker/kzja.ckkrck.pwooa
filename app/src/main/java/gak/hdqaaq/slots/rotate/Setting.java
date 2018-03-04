package gak.hdqaaq.slots.rotate;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import gak.hdqaaq.slots.gambit.GrowButton;
import gak.hdqaaq.slots.kektus.Total;


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
		CCSprite im_back = CCSprite.sprite(Total._getImg("setting/setting"));
		Total.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = GrowButton.button(Total._getImg("setting/onBtn"), Total._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= GrowButton.button(Total._getImg("setting/off"), Total._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(Total._getX(768), Total._getY(332));
		off1.setPosition(Total._getX(768), Total._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = GrowButton.button(Total._getImg("setting/onBtn"), Total._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= GrowButton.button(Total._getImg("setting/off"), Total._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(Total._getX(768), Total._getY(194));
		off2.setPosition(Total._getX(768), Total._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		GrowButton back = GrowButton.button(Total._getImg("setting/backBtn"), Total._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(Total._getX(877), Total._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(Total.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(Total.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(Total.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			Total.bgmState = false;
			Total.pauseSound();
			Total.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			Total.bgmState = true;
			if(Total.stopSound){
				Total.resumeSound();
				Total.stopSound = false;
			}else{
				Total.playSound();
			}
		}
		Total.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(Total.effectState){
			Total.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			Total.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		Total.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		Total.playEffect(Total.click);
		Total.titleState = false;
		if(Total.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		Total.playEffect(Total.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		Total.playEffect(Total.click);
		getStateBgm();
	}
}