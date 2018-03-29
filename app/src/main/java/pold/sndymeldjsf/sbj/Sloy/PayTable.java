package pold.sndymeldjsf.sbj.Sloy;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;


import pold.sndymeldjsf.sbj.Nene.GrowButton;
import pold.sndymeldjsf.sbj.slotmania.Resursi;

public class PayTable extends CCLayer
{
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new PayTable());
		return scene;
	}
/***************************************************************************************************************************************************************************************************************/	
	public PayTable()
	{
		super();
		
		CCSprite im_back = CCSprite.sprite(Resursi._getImg(String.format("backImages/pay_table%d-hd", Resursi.curLevel)));
		Resursi.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);	
		
		GrowButton retu =GrowButton.button(Resursi._getImg("Buttons/return"), Resursi._getImg("Buttons/return"),this,"returnPayTable",0);
	
		retu.setPosition(Resursi._getX(889), Resursi._getY(540));
		addChild(retu);
		
	}
/***************************************************************************************************************************************************************************************************************/	
	public void returnPayTable(Object sender){
		Resursi.playEffect(Resursi.click);
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, GameLayer.scene()));		
	}
}