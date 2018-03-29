package pold.sndymeldjsf.sbj.Sloy;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import pold.sndymeldjsf.sbj.Nene.GrowButton;
import pold.sndymeldjsf.sbj.slotmania.Resursi;


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
		CCSprite im_back = CCSprite.sprite(Resursi._getImg("setting/setting"));
		Resursi.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = GrowButton.button(Resursi._getImg("setting/onBtn"), Resursi._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= GrowButton.button(Resursi._getImg("setting/off"), Resursi._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(Resursi._getX(768), Resursi._getY(332));
		off1.setPosition(Resursi._getX(768), Resursi._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = GrowButton.button(Resursi._getImg("setting/onBtn"), Resursi._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= GrowButton.button(Resursi._getImg("setting/off"), Resursi._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(Resursi._getX(768), Resursi._getY(194));
		off2.setPosition(Resursi._getX(768), Resursi._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		GrowButton back = GrowButton.button(Resursi._getImg("setting/backBtn"), Resursi._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(Resursi._getX(877), Resursi._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(Resursi.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(Resursi.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(Resursi.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			Resursi.bgmState = false;
			Resursi.pauseSound();
			Resursi.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			Resursi.bgmState = true;
			if(Resursi.stopSound){
				Resursi.resumeSound();
				Resursi.stopSound = false;
			}else{
				Resursi.playSound();
			}
		}
		Resursi.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(Resursi.effectState){
			Resursi.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			Resursi.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		Resursi.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		Resursi.playEffect(Resursi.click);
		Resursi.titleState = false;
		if(Resursi.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		Resursi.playEffect(Resursi.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		Resursi.playEffect(Resursi.click);
		getStateBgm();
	}
}