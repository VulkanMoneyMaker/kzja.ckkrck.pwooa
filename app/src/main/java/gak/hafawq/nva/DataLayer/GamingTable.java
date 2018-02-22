package gak.hafawq.nva.DataLayer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import gak.hafawq.nva.Other.NextGameButton;
import gak.hafawq.nva.ui.Resources;

public class GamingTable extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new GamingTable());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public GamingTable()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(Resources._getImg(String.format("backImages/pay_table%d-hd", Resources.curLevel)));
		Resources.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		NextGameButton retu = NextGameButton.button(Resources._getImg("Buttons/return"), Resources._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(Resources._getX(889), Resources._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		Resources.playEffect(Resources.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}