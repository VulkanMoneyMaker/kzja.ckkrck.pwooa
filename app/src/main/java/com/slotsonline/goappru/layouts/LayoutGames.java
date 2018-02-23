package com.slotsonline.goappru.layouts;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.slotsonline.goappru.models.Buttons;
import com.slotsonline.goappru.activities.Data;

import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class LayoutGames extends CCLayer
{
	
	public final int	      z_backgraound = 0;
	public final int	      z_frame       = 1;
	public final int	      z_particle    = 2;
	public final int	      z_button      = 3;
	public final int	      z_label       = 4;
	public final int	      z_hold        = 5;
	public final int          z_coin        = 6;
	public final int	      tag_Frame     = 1;
	public final int	      tag_coin      = 2;
	
	public LayoutEngine m_Eng;
	public CCSprite []        m_sprCharacter = new CCSprite[Data.CHARACTER_COUNT];
	public CCSprite           sprLine;
	public Vector<LayoutAnimations>   arrCoin = new Vector<LayoutAnimations>();
	public CCSprite[]         m_arrLine = new CCSprite[9];
	public CCSprite[]         m_arrHold = new CCSprite[5];	
 	public CCSprite[][]       m_arrLocked = new CCSprite[4][2];
 	public Vector<CCSprite>   m_arrRect = new Vector<CCSprite>();
	
	public CCLabel            m_lblCoin;
	public CCLabel            m_lblLines;
	public CCLabel            m_lblBets;
	public CCLabel            m_lblMaxLines;
	public CCLabel            m_lblWin;		
	
	public int                m_nTouchCol;
	public int                coinCount     = 0;
	public int                curRctCount   = 0;
    public int                m_nSlotTick;	
	public int                m_nFrameCount;
	public int                m_nCurScore;
	public int                m_nOldScore;
	public int                m_nIncrease;
	
	public float              m_nSTPointY;
	public float              lastPosX = Data._getX(319);
	public float              lastPosY = Data._getY(534);
	
	public boolean            m_bDrawRuleLine;   
	
	public boolean            m_bIsAnim;
	
	public boolean            m_bIncrease;
	public boolean            m_ArrRectState;
	 
 	
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new LayoutGames());
		return scene;
	}
/***************************************************CONSTRACTOR*******************************************************************************************************************************************************/	
	public LayoutGames()
	{
		super();	
		
		m_Eng = new LayoutEngine();
		initVariables();
		initImages();
		initButton();
		initLabels();
		drawLine(true);	
		schedule("onTime", 1.0f/60.0f);
	}
/***************************************************VALUE FORMART*******************************************************************************************************************************************************/
	public void initVariables(){
		isTouchEnabled_= true;
		m_nSlotTick = -1;				
		m_Eng.setCardBettwenY(Data.CARD_BETWEEN_Y);
		
	}
/***************************************************LOAD IMAGES*******************************************************************************************************************************************************/
	public void initImages(){
		int nCurStage = Data.curLevel;
		CCSprite im_back = CCSprite.sprite(Data._getImg(String.format("backImages/game_bg%d-hd",nCurStage)));
		Data.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
		
		for(int i = 0; i < Data.CHARACTER_COUNT ; i++){
			m_sprCharacter[i] = CCSprite.sprite(Data._getImg(String.format("character/stage%d/%s",  Data.curLevel, Data.strIconName[i])));
			Data.setScale(m_sprCharacter[i]);
			m_sprCharacter[i].setAnchorPoint(0, 0);
		}
		for(int i = 0; i < Data.RULE_LINE_COUNT ; i++){
			m_arrLine[i] = CCSprite.sprite(Data._getImg(String.format("lines/line%d", i + 1)));
			Data.setScale(m_arrLine[i]);
			m_arrLine[i].setAnchorPoint(0, 0);
			addChild(m_arrLine[i]);
			m_arrLine[i].setPosition(Data._getX(Data.lineX), Data._getY(Data.lineY[i]));
			m_arrLine[i].setVisible(false);			
		}
		for(int i = 0; i < Data.COL_ ; i++){
			m_arrHold[i] = CCSprite.sprite(Data._getImg("Buttons/hold"));
			Data.setScale(m_arrHold[i]);
			m_arrHold[i].setAnchorPoint(0, 0);
			m_arrHold[i].setPosition(Data._getX(116 + i * 146), Data._getY(125));
			this.addChild(m_arrHold[i],z_hold);		
		}
	}
/***************************************************BUTTONS LOAD*******************************************************************************************************************************************************/
	public void initButton(){
		Buttons back = Buttons.button(Data._getImg("Buttons/back1"), Data._getImg("Buttons/back2"),this,"onBack",0);
		Buttons coin = Buttons.button(Data._getImg("Buttons/addCoin1"), Data._getImg("Buttons/addCoin2"),this,"onCoinBuy",0);
		//Buttons paytable = Buttons.button(Data._getImg("Buttons/paytable1"), Data._getImg("Buttons/paytable2"),this,"onPlayTable",0);
		Buttons line = Buttons.button(Data._getImg("Buttons/line1"), Data._getImg("Buttons/line2"),this,"onLines",0);
		Buttons maxline = Buttons.button(Data._getImg("Buttons/maxlines1"), Data._getImg("Buttons/maxlines2"),this,"onMaxLines",0);
		Buttons bet = Buttons.button(Data._getImg("Buttons/bet1"), Data._getImg("Buttons/bet2"),this,"onBet",0);
		Buttons spin = Buttons.button(Data._getImg("Buttons/spin1"), Data._getImg("Buttons/spin1"),this,"onSpin",0);
		Buttons setting = Buttons.button(Data._getImg("Buttons/setting1"), Data._getImg("Buttons/setting2"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Data._getX(50), Data._getY(586));
		
		back.setPosition(Data._getX(908), Data._getY(602));
		coin.setPosition(Data._getX(76), Data._getY(58));
		//paytable.setPosition(Data._getX(76),Data._getY(58));
		if(Data.curLevel == 1 || Data.curLevel == 3){
			line.setPosition(Data._getX(232), Data._getY(34));
			maxline.setPosition(Data._getX(431), Data._getY(34));
			bet.setPosition(Data._getX(630), Data._getY(34));
			spin.setPosition(Data._getX(908), Data._getY(55));
		}else if(Data.curLevel == 2){
			line.setPosition(Data._getX(255), Data._getY(34));
			maxline.setPosition(Data._getX(436), Data._getY(34));
			bet.setPosition(Data._getX(612), Data._getY(34));
			spin.setPosition(Data._getX(908), Data._getY(55));
		}else if(Data.curLevel == 4){
			line.setPosition(Data._getX(254), Data._getY(34));
			maxline.setPosition(Data._getX(427), Data._getY(34));
			bet.setPosition(Data._getX(610), Data._getY(34));
			spin.setPosition(Data._getX(860), Data._getY(55));
		}else if(Data.curLevel == 5){
			line.setPosition(Data._getX(252), Data._getY(34));
			maxline.setPosition(Data._getX(427), Data._getY(34));
			bet.setPosition(Data._getX(609), Data._getY(34));
			spin.setPosition(Data._getX(870), Data._getY(55));
		}else if(Data.curLevel == 6){
			line.setPosition(Data._getX(252), Data._getY(34));
			maxline.setPosition(Data._getX(431), Data._getY(34));
			bet.setPosition(Data._getX(610), Data._getY(34));
			spin.setPosition(Data._getX(860), Data._getY(55));
		}
		addChild(back);
		addChild(setting);
		addChild(coin);
		//addChild(paytable);	
		addChild(line);	
		addChild(maxline);
		addChild(bet);
		addChild(spin);
	}
/*********************************************************************************LABELS LOAD**************************************************************************************************************************/
	public void initLabels(){
		ccColor3B clr = ccColor3B.ccc3(255, 255, 255);
		m_lblCoin = CCLabel.makeLabel(String.format("%d", m_Eng.m_nGameCoin), Data._getFont("Imagica"), 40);
		Data.setScale(m_lblCoin);
		m_lblCoin.setAnchorPoint(0, 0);
		m_lblCoin.setPosition(Data._getX(200), Data._getY(544));
		m_lblCoin.setColor(clr);
		addChild(m_lblCoin);
		
		m_lblLines = CCLabel.makeLabel(String.format("%d", m_Eng.m_nRuleLineCount), Data._getFont("Imagica"), 30);
		Data.setScale(m_lblLines);
		m_lblLines.setAnchorPoint(0, 0);
		m_lblLines.setPosition(Data._getX(225), Data._getY(65));
		m_lblLines.setColor(clr);
		addChild(m_lblLines);
		
		m_lblMaxLines = CCLabel.makeLabel(String.format("%d", m_Eng.m_nMaxLineCount), Data._getFont("Imagica"), 30);
		Data.setScale(m_lblMaxLines);
		m_lblMaxLines.setAnchorPoint(0, 0);
		m_lblMaxLines.setPosition(Data._getX(421), Data._getY(65));
		m_lblMaxLines.setColor(clr);
		addChild(m_lblMaxLines);
		
		m_lblBets = CCLabel.makeLabel(String.format("%d",m_Eng.m_nBet), Data._getFont("Imagica"), 30);
		Data.setScale(m_lblBets);
		m_lblBets.setAnchorPoint(0, 0);
		m_lblBets.setPosition(Data._getX(615), Data._getY(65));
		m_lblBets.setColor(clr);
		addChild(m_lblBets);
		
		m_lblWin = CCLabel.makeLabel(String.format("%d",m_Eng.m_nWin), Data._getFont("Imagica"), 30);
		Data.setScale(m_lblWin);
		m_lblWin.setAnchorPoint(0, 0);
		if(Data.curLevel == 4 || Data.curLevel == 5 || Data.curLevel ==6)
			m_lblWin.setPosition(Data._getX(760), Data._getY(25));
		else
			m_lblWin.setPosition(Data._getX(800), Data._getY(25));
		m_lblWin.setColor(clr);
		addChild(m_lblWin);			
	}
/*********************************************************************************SCHEDULE**************************************************************************************************************************/
	public void onTime(float dt){			
		controlSlot();
	}
	
/*********************************************************************************DRAW RECTS**************************************************************************************************************************/
	public void effectRect(float dt){		
		for(int i = 0 ; i < m_arrRect.size() ; i++){			
			if( m_arrRect.get(i).getVisible())
				m_arrRect.get(i).setVisible(false);
			else
				m_arrRect.get(i).setVisible(true);
				
		}		
	}
/*********************************************************************************START SLOT**************************************************************************************************************************/
	public void startSlot(){
		if(m_Eng.m_bStartSlot)
			return;
		if(m_Eng.m_nGameCoin < m_Eng.m_nBet * m_Eng.m_nRuleLineCount){
			showAlert();
			return;
		}
		m_Eng.m_nWin = 0;
		m_lblWin.setString(String.format("%d", m_Eng.m_nWin));
		if(m_bIsAnim){
			m_bIsAnim = false;
		}
		if(m_ArrRectState){
			for(int i = 0 ; i < m_arrRect.size() ; i++){
				m_arrRect.get(i).setVisible(false);
			}			
			unschedule("effectRect");
			m_arrRect.clear();			
			m_ArrRectState = false;
		}
		m_nSlotTick = 0;
		Data.TGameResult.clear();
		drawLine(false);
		for(int i = 0; i < Data.COL_ ; i++)
			m_Eng.m_bSloting[i] = true;		
	}
/*********************************************************************************SLOT CONTROL**************************************************************************************************************************/
	public void controlSlot(){		
		m_Eng.processSlot(m_nSlotTick);
		if(m_Eng.isStopAllSlots()){			
			if(m_Eng.m_bStartSlot){	
				m_Eng.m_bStartSlot = false;
				schedule("compareCards", 0.5f);
			}
		}else
			m_Eng.m_bStartSlot = true;		
		if(m_nSlotTick > -1){
			m_nSlotTick++;
			if(m_nSlotTick > 2 * Data.TICK){
				int nCols = m_nSlotTick / Data.TICK -1;
				if(m_nSlotTick % 10 == 0){
					if(nCols < 6)
						m_Eng.m_strState[nCols - 1] = "last";
					else if(m_Eng.m_bSloting[Data.COL_ - 1] == false)
						m_nSlotTick = -1;
				}
				
			}
		}
	}
/*************************************************************************************************************************************************************************************************************************/
	public void changeLabel(float dt){
		m_nOldScore += m_nIncrease;
		if(m_nOldScore >= m_nCurScore){
			m_nOldScore  = m_nCurScore;
			m_lblCoin.setString(String.format("%d", m_nCurScore));
			m_bIncrease = false;
			unschedule("changeLabel");			
		}else
			m_lblCoin.setString(String.format("%d", m_nOldScore));
	}
/*********************************************************************************COMPARE CARDS**************************************************************************************************************************/	
	public void compareCards(float dt){		
		float nCardX = Data.CARD_STRT_X ;
	    float nCardY = Data.CARD_STRT_Y;
	    float nCardBetweenX = Data.CARD_BETWEEN_X;
	    float nCardBetweenY = Data.CARD_BETWEEN_Y;
	    unschedule("compareCards");
	    int nPrevCoin = m_Eng.m_nGameCoin;
	    m_Eng.compareCards();
	    if(m_Eng.m_bHit) {	  	    	
	    	schedule("coinAnim", 1.0f / 15.0f);
	    }
	    m_lblWin.setString(String.format("%d", m_Eng.m_nWin));
	    if(m_Eng.m_nGameCoin > nPrevCoin){
	    	m_bIncrease = true;
	    	m_nCurScore = m_Eng.m_nGameCoin;
	    	m_nOldScore = nPrevCoin;
	    	if(m_nCurScore - m_nOldScore >= 5000){
	    		m_nIncrease = 1253;
	    	}else if(m_nCurScore - m_nOldScore < 5000 && m_nCurScore - m_nOldScore >= 1000){
				m_nIncrease = 125;
			}else if(m_nCurScore - m_nOldScore < 1000 && m_nCurScore - m_nOldScore >= 100){
				m_nIncrease = 15;
			}else{
				m_nIncrease = 2;
			}
	    	schedule("changeLabel", 1.0f / 60.0f);	    	
	    }else
	    	m_lblCoin.setString(String.format("%d", m_Eng.m_nGameCoin));
	    if(m_Eng.isStopAllSlots()) {
	        if( (Data.TGameResult.size() > 0) && !m_bIsAnim ){
	            m_bIsAnim= true;
	           for(int i = 0; i < Data.TGameResult.size() ; i++){
	        	   int nRuleIndex = Data.TGameResult.get(i).nRuleLineIndex;
	        	   int nEqualCount = Data.TGameResult.get(i).nEqualCount;
	        	   //int nCharIndex = Data.TGameResult.get(i).nCharacterIndex;
	        	   m_arrLine[nRuleIndex].setVisible(true);
	        	   for(int j = 0 ; j < nEqualCount ; j++){
	        		   CGPoint ptCardPos = new CGPoint();
	        		   ptCardPos.set(Data._getX(nCardX + m_Eng.nArrRules[nRuleIndex][j][1] * nCardBetweenX),
	        				   Data._getY(nCardY - (m_Eng.nArrRules[nRuleIndex][j][0] - 1) * nCardBetweenY));
	        		   CCSprite rect = CCSprite.sprite(Data._getImg("Buttons/rect"));
	        	       Data.setScale(rect);
	        		   rect.setAnchorPoint(0,0);
	        		   rect.setPosition(ptCardPos.x, ptCardPos.y);
	        		   addChild(rect, z_frame, tag_Frame);	        		  
	        		   m_arrRect.add(rect);    
	        		   m_ArrRectState = true;
	        	   }	        	   
	           }            
	        }
	    }	
	    if(m_ArrRectState)
	    	schedule("effectRect", 0.5f);
	    	
	}	
/***********************************************************************************************************************************************************************************************************/	
	@Override public void draw(GL10 gl) {		
		drawCharacters(gl);
		drawHolds();
	}
/*********************************************************************************DRAW CHARACTERS**************************************************************************************************************************/
	public void drawCharacters(GL10 gl){
		gl.glColor4f(0, 0, 0, 1);		
		gl.glLineWidth(Data._getX(0.6f));
		float nCardX = Data.CARD_STRT_X;
	    float nCardY = Data.CARD_STRT_Y;
	    float nCardBetweenX = Data.CARD_BETWEEN_X;
	    float nCardBetweenY = Data.CARD_BETWEEN_Y;
	    for(int i = 0; i < Data.ROW_; i++){
	        for(int j = 0; j < Data.COL_; j++){
	            int nCardType = m_Eng.m_nArrSlot[i][j];
	            CCSprite spr = m_sprCharacter[nCardType];
	            spr.setPosition(Data._getX(nCardX) + j * Data._getX(nCardBetweenX), Data._getY(nCardY) - (i - 1)  * Data._getY(nCardBetweenY) - m_Eng.m_fMovingY[j]);
	            spr.visit(gl);
	            
	        }
	    }    
	}
/*********************************************************************************DRAW HOLDS**************************************************************************************************************************/
	public void drawHolds(){
		for (int i = 0; i < Data.COL_; i++) {
	        if (!m_Eng.m_bSloting[i]) {
	        	m_arrHold[i].setVisible(false);	           
	        }
	        else{
	        	m_arrHold[i].setVisible(true);	            
	        }
	            
	    }
	}
/*********************************************************************************DRAW LINS**************************************************************************************************************************/
	public void drawLine(boolean bDrawRuleLine){		   
		    for(int i = 0; i < Data.RULE_LINE_COUNT; i++){
		        if (i <  m_Eng.m_nRuleLineCount)
		            m_arrLine[i].setVisible(bDrawRuleLine) ;
		        else 
		        	m_arrLine[i].setVisible(false);
		    }
		
	}
/*********************************************************************************COIN ANIMATIONS**************************************************************************************************************************/
	public void coinAnim(float dt){
		if(arrCoin.size() < 15){
			LayoutAnimations coin = new LayoutAnimations();
			addChild(coin, z_coin, tag_coin);
			coin.setPosition(CCDirector.sharedDirector().winSize().width / 2, Data._getY(90));
			arrCoin.add(coin);
		}else if(arrCoin.get(14).getPosition().y > CCDirector.sharedDirector().winSize().height - Data._getY(40)){
			arrCoin.clear();
			unschedule("coinAnim");
		}
	}
/******************************************************************SET INFO**************************************************************************************************************************/
	public void setInfo(){
		Data.allCoin = m_Eng.m_nGameCoin;
		Data.curLine = m_Eng.m_nRuleLineCount;
		Data.maxline = m_Eng.m_nMaxLineCount;
		Data.bet = m_Eng.m_nBet;
		Data.saveSetting();
	}
/*********************************************************************************BUTTONS DEFINE**************************************************************************************************************************/
	public void onBack(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		Data.titleState = false;
		setInfo();			
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutTitles.scene()));
	}
	
	public void onCoinBuy(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.GAME_STATE = "game";
		Data.playEffect(Data.click);
		setInfo();
		Data.payTableFlag = true;
		for(int i = 0; i < Data.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Data.COL_ ; j++){
				if(i == 0){
					Data.rowIndex[i] = m_Eng.m_nRowIndex[i];
				}
				if(i < Data.ROW_)
					Data.arrSlot[i][j] = m_Eng.m_nArrSlot[i][j];
				Data.arrTempSlot[i][j] = m_Eng.m_nArrTempSlot[i][j];
			}
		}		
		//if (VunglePub.isVideoAvailable(true))
		//	VunglePub.displayIncentivizedAdvert(true);	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutCoins.scene()));
		
	}
	public void onPlayTable(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		setInfo();
		Data.payTableFlag = true;
		for(int i = 0; i < Data.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Data.COL_ ; j++){
				if(i == 0){
					Data.rowIndex[i] = m_Eng.m_nRowIndex[i];
				}
				if(i < Data.ROW_)
					Data.arrSlot[i][j] = m_Eng.m_nArrSlot[i][j];
				Data.arrTempSlot[i][j] = m_Eng.m_nArrTempSlot[i][j];
			}
		}		
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutTables.scene()));
	}
	public void onLines(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		m_Eng.m_nRuleLineCount++;
		if(m_Eng.m_nRuleLineCount > m_Eng.m_nMaxLineCount)
			m_Eng.m_nRuleLineCount = 1;
		m_lblLines.setString(String.format("%d", m_Eng.m_nRuleLineCount));
		drawLine(true);
	}
	public void onMaxLines(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		m_Eng.m_nMaxLineCount++;
		if (m_Eng.m_nMaxLineCount > Data.RULE_LINE_COUNT)
			m_Eng.m_nMaxLineCount = 1;			
		m_Eng.m_nRuleLineCount = m_Eng.m_nMaxLineCount;
		m_lblLines.setString(String.format("%d", m_Eng.m_nRuleLineCount));
		m_lblMaxLines.setString(String.format("%d", m_Eng.m_nMaxLineCount));
		drawLine(true);
		
	}
	
	public void onBet(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		m_Eng.m_nBet++;
		if(m_Eng.m_nBet > 10)
			m_Eng.m_nBet = 1;
		m_lblBets.setString(String.format("%d", m_Eng.m_nBet));
	}
	public void onSpin(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Data.playEffect(Data.click);
		startSlot();	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Data.playEffect(Data.click);
		Data.titleState = true;
		Data.GAME_STATE = "game";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutSettings.scene()));
	}
/*********************************************************************************ALERT**************************************************************************************************************************/
	public void showAlert(){
		CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(CCDirector.sharedDirector().getActivity());
					builder.setMessage("Insufficient.Get some free coins.")
						.setCancelable(false)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, LayoutCoins.scene()));
									Data.GAME_STATE = "game";
									dialog.cancel();
	                             }
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {								
									if(Data.allCoin <= 0 && !Data.setTimeState){
										Data.currentTime = System.currentTimeMillis() / 1000 ;
										Data.setTimeState = true;
										Data.saveSetting();
									}
									dialog.cancel();
								}
						});
						AlertDialog alert = builder.create();
						alert.show();
			}
		});
			
	}
	
	
	
	
}