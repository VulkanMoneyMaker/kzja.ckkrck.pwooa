package newslots.playnow.topcom.dates;


import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.nodes.CCLabel;
import newslots.playnow.topcom.trash.GrowButton;
import newslots.playnow.topcom.all.G;


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
		 if(G.allCoin == 0){
			 if(G.setTimeState){
				 long time = System.currentTimeMillis() / 1000;
				 if((time - G.currentTime) / 3600 >= 24){
					 G.allCoin = 250;
					 G.setTimeState = false;
					 G.saveSetting();
				 }
			 }			 
		 }		
	}
/***************************************************************************************************************************************************************************************************************/
	public void createBG(){
		CCSprite im_back = CCSprite.sprite(G._getImg("backImages/menu_bg-hd"));
		G.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createButton(){
		String [] str = {"Buttons/cash","Buttons/pirates","Buttons/jewels","Buttons/fruit","Buttons/cash","Buttons/dragons"};
		GrowButton selectBtn;
		for(int i = 0 ; i < 1 ; i++){
			selectBtn = GrowButton.button(G._getImg(str[i]),G._getImg(str[i]),this,"startGame",(i+1));
//			float fx =  G._getX(170) + G._getX(307) * (i % 3);
//			float fy = G._getY(440) - G._getY(228) * (i / 3);
			float fx = 900;
			float fy = 350;
			selectBtn.setAnchorPoint(0, 0);
			selectBtn.setPosition(fx, fy);
			addChild(selectBtn);
		}
		
		CCSprite img_txt = CCSprite.sprite(G._getImg("Buttons/text_box"));
		G.setScale(img_txt);
		img_txt.setAnchorPoint(0, 0);
		img_txt.setPosition(G._getX(52),G._getY(564));
		addChild(img_txt);
		
		
		CCSprite img_usd = CCSprite.sprite(G._getImg("Buttons/usd3"));
		G.setScale(img_usd);
		img_usd.setAnchorPoint(0, 0);
		img_usd.setPosition(G._getX(40), G._getY(564));
		addChild(img_usd);		
		
		GrowButton plus =GrowButton.button(G._getImg("Buttons/plus1"), G._getImg("Buttons/plus2"),this,"plusCoin",0);			
		plus.setAnchorPoint(0, 0);
		plus.setPosition(G._getX(288),G._getY(597));
		addChild(plus);
		
		GrowButton setting = GrowButton.button(G._getImg("Buttons/setting1"), G._getImg("Buttons/setting1"), this, "setting",0);		
		setting.setAnchorPoint(0, 0);
		setting.setPosition(G._getX(100),G._getY(38));
		addChild(setting);
		
		//GrowButton more_game = GrowButton.button(G._getImg("Buttons/more_game"), G._getImg("Buttons/more_game"), this, "moreGame", 0);
		//more_game.setAnchorPoint(0, 0);
		//more_game.setPosition(G._getX(824),G._getY(38));
		//addChild(more_game);
	}
/***************************************************************************************************************************************************************************************************************/
	public void createLabel(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		CCLabel coinLabel = CCLabel.makeLabel(String.format("%d", G.allCoin), G._getFont("Imagica"), 30);
		G.setScale(coinLabel);
		coinLabel.setAnchorPoint(0, 0);
		coinLabel.setPosition(G._getX(160),G._getY(580));
		coinLabel.setColor(clr);
		addChild(coinLabel);	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void startGame(Object sender) {
		G.playEffect(G.click);
		G.titleState = true;
		G.curLevel = ((CCMenuItem)sender).getTag();	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void plusCoin(Object sender) {
		G.playEffect(G.click);
		G.GAME_STATE = "title";
		G.titleState = true;
		
		//	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
		
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		G.playEffect(G.click);
		G.titleState = true;
		G.GAME_STATE = "title";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
	}
/***************************************************************************************************************************************************************************************************************/
	public void moreGame(Object sender){
		G.playEffect(G.click);
	}
	
	
}