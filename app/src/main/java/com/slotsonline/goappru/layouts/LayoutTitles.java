package com.slotsonline.goappru.layouts;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;

import com.slotsonline.goappru.models.Buttons;
import com.slotsonline.goappru.activities.Data;


public class LayoutTitles extends CCLayer
{

	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutTitles());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public LayoutTitles()
	{
		super();
		
		schedule("getInfo", 1.0f / 10.0f);
	}	
	public void getInfo(float dt){
		unschedule("getInfo");
		createBG();
		createButton();
		createLabel();	
		getTime();
		
	}
	public void getTime(){
		 if(Data.allCoin == 0){
			 if(Data.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - Data.currentTime) / 3600 >= 24){
					 Data.allCoin = 250;
					 Data.setTimeState = false;
					 Data.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(Data._getImg("backImages/menu_bg-hd"));
		Data.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/fruit","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};
		Buttons selectBtn;
		for(int i = 0 ; i < 1 ; i++){
			selectBtn = Buttons.button(Data._getImg(str[i]), Data._getImg(str[i]),this,"startGame",(i+1));
//			float fx =  Data._getX(170) + Data._getX(307) * (i % 3);
//			float fy = Data._getY(440) - Data._getY(228) * (i / 3);
			float fx = 900;
			float fy = 350;
			selectBtn.setAnchorPoint(0, 0);
			selectBtn.setPosition(fx, fy);
			addChild(selectBtn);
		}
		
		CCSprite img_txt = CCSprite.sprite(Data._getImg("Buttons/text_box"));
		Data.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(Data._getX(52), Data._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(Data._getImg("Buttons/usd3"));
		Data.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(Data._getX(40), Data._getY(564));
		addChild(img_usd);		
		
		Buttons plus = Buttons.button(Data._getImg("Buttons/plus1"), Data._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(Data._getX(288), Data._getY(597));
		addChild(plus);
		
		Buttons setting = Buttons.button(Data._getImg("Buttons/setting1"), Data._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Data._getX(100), Data._getY(38));
		addChild(setting);
		
		//Buttons more_game = Buttons.button(Data._getImg("Buttons/more_game"), Data._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(Data._getX(824),Data._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", Data.allCoin), Data._getFont("Imagica"), 30);
		Data.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(Data._getX(160), Data._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		Data.playEffect(Data.click);
		Data.titleState = true;
		Data.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutGames.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		Data.playEffect(Data.click);
		Data.GAME_STATE = "title";
		Data.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutCoins.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Data.playEffect(Data.click);
		Data.titleState = true;
		Data.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutSettings.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		Data.playEffect(Data.click);
	}
	
	
}