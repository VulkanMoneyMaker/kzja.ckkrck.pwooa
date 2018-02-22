package gak.hafawq.nva.DataLayer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import gak.hafawq.nva.Other.NextGameButton;
import gak.hafawq.nva.ui.Resources;


public class CurrentGameSetting extends CCLayer
{
	NextGameButton on1;
	NextGameButton off1;
	NextGameButton on2;
	NextGameButton off2;
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new CurrentGameSetting());
		return scene;
	}
/*****************************************************************************************************************************************************************************************************************/	
	public CurrentGameSetting()
	{
		super();
		CCSprite im_back = CCSprite.sprite(Resources._getImg("setting/setting"));
		Resources.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		on1 = NextGameButton.button(Resources._getImg("setting/onBtn"), Resources._getImg("setting/onBtn"),this,"setOnOff1",0);
		off1= NextGameButton.button(Resources._getImg("setting/off"), Resources._getImg("setting/off"),this,"setOnOff1",0);
		on1.setPosition(Resources._getX(768), Resources._getY(332));
		off1.setPosition(Resources._getX(768), Resources._getY(332));
		addChild(on1);
		addChild(off1);
		
		
		on2 = NextGameButton.button(Resources._getImg("setting/onBtn"), Resources._getImg("setting/onBtn"),this,"setOnOff2",0);
		off2= NextGameButton.button(Resources._getImg("setting/off"), Resources._getImg("setting/off"),this,"setOnOff2",0);
		on2.setPosition(Resources._getX(768), Resources._getY(194));
		off2.setPosition(Resources._getX(768), Resources._getY(194));
		addChild(on2);
		addChild(off2);
		
		initVisible();		
		NextGameButton back = NextGameButton.button(Resources._getImg("setting/backBtn"), Resources._getImg("setting/backBtn"),this,"back",0);
		back.setPosition(Resources._getX(877), Resources._getY(55));
		addChild(back);		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void initVisible(){
		if(Resources.bgmState){
			on2.setVisible(true);
			off2.setVisible(false);
		}else{
			on2.setVisible(false);
			off2.setVisible(true);
		}
		
		if(Resources.effectState){
			on1.setVisible(true);
			off1.setVisible(false);
		}else{
			on1.setVisible(false);
			off1.setVisible(true);
		}		
		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateBgm(){
		if(Resources.bgmState){
			on2.setVisible(false);
			off2.setVisible(true);
			Resources.bgmState = false;
			Resources.pauseSound();
			Resources.stopSound = true;
		}else{
			on2.setVisible(true);
			off2.setVisible(false);
			Resources.bgmState = true;
			if(Resources.stopSound){
				Resources.resumeSound();
				Resources.stopSound = false;
			}else{
				Resources.playSound();
			}
		}
		Resources.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void getStateEffect(){
		if(Resources.effectState){
			Resources.effectState = false;
			on1.setVisible(false);
			off1.setVisible(true);
		}else{
			Resources.effectState = true;
			on1.setVisible(true);
			off1.setVisible(false);			
		}
		Resources.saveSetting();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void back(Object sender){	
		Resources.playEffect(Resources.click);
		Resources.titleState = false;
		if(Resources.GAME_STATE.equals("title"))
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
		else
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff1(Object sender){
		Resources.playEffect(Resources.click);
		getStateEffect();
	}
/*****************************************************************************************************************************************************************************************************************/
	public void setOnOff2(Object sender){
		Resources.playEffect(Resources.click);
		getStateBgm();
	}
}