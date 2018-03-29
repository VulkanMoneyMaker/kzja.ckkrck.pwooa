package pold.sndymeldjsf.sbj.Sloy;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;
import pold.sndymeldjsf.sbj.Nene.GrowButton;
import pold.sndymeldjsf.sbj.slotmania.Resursi;


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
		 if(Resursi.allCoin == 0){
			 if(Resursi.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - Resursi.currentTime) / 3600 >= 24){
					 Resursi.allCoin = 250;
					 Resursi.setTimeState = false;
					 Resursi.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(Resursi._getImg("backImages/menu_bg-hd"));
		Resursi.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/zombies","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};	
		GrowButton selectBtn;
		for(int i = 0 ; i < 1 ; i++){
			selectBtn = GrowButton.button(Resursi._getImg(str[i]), Resursi._getImg(str[i]),this,"startGame",(i+1));
//			float fx =  Resursi._getX(170) + Resursi._getX(307) * (i % 3);
//			float fy = Resursi._getY(440) - Resursi._getY(228) * (i / 3);
			float fx = 900;
			float fy = 350;
			selectBtn.setAnchorPoint(0, 0);
			selectBtn.setPosition(fx, fy);
			addChild(selectBtn);
		}
		
		CCSprite img_txt = CCSprite.sprite(Resursi._getImg("Buttons/text_box"));
		Resursi.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(Resursi._getX(52), Resursi._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(Resursi._getImg("Buttons/usd3"));
		Resursi.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(Resursi._getX(40), Resursi._getY(564));
		addChild(img_usd);		
		
		GrowButton plus =GrowButton.button(Resursi._getImg("Buttons/plus1"), Resursi._getImg("Buttons/plus2"),this,"plusCoin",0);
		plus.setAnchorPoint(0, 0);
		plus.setPosition(Resursi._getX(288), Resursi._getY(597));
		addChild(plus);
		
		GrowButton setting = GrowButton.button(Resursi._getImg("Buttons/setting1"), Resursi._getImg("Buttons/setting1"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Resursi._getX(100), Resursi._getY(38));
		addChild(setting);
		
		//GrowButton more_game = GrowButton.button(Resursi._getImg("Buttons/more_game"), Resursi._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(Resursi._getX(824),Resursi._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", Resursi.allCoin), Resursi._getFont("Imagica"), 30);
		Resursi.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(Resursi._getX(160), Resursi._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		Resursi.playEffect(Resursi.click);
		Resursi.titleState = true;
		Resursi.curLevel = ((CCMenuItem)sender).getTag();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		Resursi.playEffect(Resursi.click);
		Resursi.GAME_STATE = "title";
		Resursi.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Resursi.playEffect(Resursi.click);
		Resursi.titleState = true;
		Resursi.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		Resursi.playEffect(Resursi.click);
	}
	
	
}