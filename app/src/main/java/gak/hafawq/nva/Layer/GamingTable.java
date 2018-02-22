package gak.hafawq.nva.Layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import gak.hafawq.nva.Other.NextGameButton;
import gak.hafawq.nva.slotmania.G;

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
		
		CCSprite im_back = CCSprite.sprite(G._getImg(String.format("backImages/pay_table%d-hd", G.curLevel)));
		G.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		NextGameButton retu = NextGameButton.button(G._getImg("Buttons/return"), G._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(G._getX(889),G._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		G.playEffect(G.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}