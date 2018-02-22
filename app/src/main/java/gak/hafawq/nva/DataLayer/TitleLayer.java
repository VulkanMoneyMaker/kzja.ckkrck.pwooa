package gak.hafawq.nva.DataLayer;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;
import gak.hafawq.nva.Other.NextGameButton;
import gak.hafawq.nva.ui.Resources;


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
		 if(Resources.allCoin == 0){
			 if(Resources.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - Resources.currentTime) / 3600 >= 24){
					 Resources.allCoin = 250;
					 Resources.setTimeState = false;
					 Resources.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(Resources._getImg("backImages/menu_bg-hd"));
		Resources.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/zombies","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};	
		NextGameButton selectBtn;
		for(int i = 0 ; i < 1 ; i++){
			selectBtn = NextGameButton.button(Resources._getImg(str[i]), Resources._getImg(str[i]),this,"startGame",(i+1));
//			float fx =  Resources._getX(170) + Resources._getX(307) * (i % 3);
//			float fy = Resources._getY(440) - Resources._getY(228) * (i / 3);
			float fx = 900;
			float fy = 350;
			selectBtn.setAnchorPoint(0, 0);
			selectBtn.setPosition(fx, fy);
			addChild(selectBtn);
		}
		
		CCSprite img_txt = CCSprite.sprite(Resources._getImg("Buttons/text_box"));
		Resources.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(Resources._getX(52), Resources._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(Resources._getImg("Buttons/usd3"));
		Resources.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(Resources._getX(40), Resources._getY(564));
		addChild(img_usd);		
		
		NextGameButton plus = NextGameButton.button(Resources._getImg("Buttons/plus1"), Resources._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(Resources._getX(288), Resources._getY(597));
		addChild(plus);
		
		NextGameButton setting = NextGameButton.button(Resources._getImg("Buttons/setting1"), Resources._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Resources._getX(100), Resources._getY(38));
		addChild(setting);
		
		//NextGameButton more_game = NextGameButton.button(Resources._getImg("Buttons/more_game"), Resources._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(Resources._getX(824),Resources._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", Resources.allCoin), Resources._getFont("Imagica"), 30);
		Resources.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(Resources._getX(160), Resources._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		Resources.playEffect(Resources.click);
		Resources.titleState = true;
		Resources.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		Resources.playEffect(Resources.click);
		Resources.GAME_STATE = "title";
		Resources.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, ItemForBuy.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Resources.playEffect(Resources.click);
		Resources.titleState = true;
		Resources.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CurrentGameSetting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		Resources.playEffect(Resources.click);
	}
	
	
}