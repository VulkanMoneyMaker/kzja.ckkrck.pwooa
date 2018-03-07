package com.fkkjqa.fklefkwj.rotate;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;
import com.fkkjqa.fklefkwj.gambit.GrowButton;
import com.fkkjqa.fklefkwj.kektus.Total;


public class TitleLayer extends CCLayer
{

	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new TitleLayer());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/
	public TitleLayer()
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
		 if(Total.allCoin == 0){
			 if(Total.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - Total.currentTime) / 3600 >= 24){
					 Total.allCoin = 250;
					 Total.setTimeState = false;
					 Total.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(Total._getImg("backImages/menu_bg-hd"));
		Total.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/dragons","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};
		GrowButton selectBtn;
		for(int i = 0 ; i < 1 ; i++){
			selectBtn = GrowButton.button(Total._getImg(str[i]), Total._getImg(str[i]),this,"startGame",(i+1));
			float fx = 900;
			float fy = 350;
			selectBtn.setAnchorPoint(0, 0);
			selectBtn.setPosition(fx, fy);
			addChild(selectBtn);
		}
		
		CCSprite img_txt = CCSprite.sprite(Total._getImg("Buttons/text_box"));
		Total.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(Total._getX(52), Total._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(Total._getImg("Buttons/usd3"));
		Total.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(Total._getX(40), Total._getY(564));
		addChild(img_usd);		
		
		GrowButton plus =GrowButton.button(Total._getImg("Buttons/plus1"), Total._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(Total._getX(288), Total._getY(597));
		addChild(plus);
		
		GrowButton setting = GrowButton.button(Total._getImg("Buttons/setting1"), Total._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Total._getX(100), Total._getY(38));
		addChild(setting);
		
		//GrowButton more_game = GrowButton.button(Total._getImg("Buttons/more_game"), Total._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(Total._getX(824),Total._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", Total.allCoin), Total._getFont("Imagica"), 30);
		Total.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(Total._getX(160), Total._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		Total.playEffect(Total.click);
		Total.titleState = true;
		Total.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		Total.playEffect(Total.click);
		Total.GAME_STATE = "title";
		Total.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Total.playEffect(Total.click);
		Total.titleState = true;
		Total.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		Total.playEffect(Total.click);
	}
	
	
}