package gak.hdqaaq.slots.rotate;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

import android.app.AlertDialog;
import android.content.DialogInterface;

import gak.hdqaaq.slots.kektus.Total;

import gak.hdqaaq.slots.gambit.GrowButton;

import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

public class GameLayer extends CCLayer
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
	
	public Engine             m_Eng;
	public CCSprite []        m_sprCharacter = new CCSprite[Total.CHARACTER_COUNT];
	public CCSprite           sprLine;
	public Vector<CoinAnim>   arrCoin = new Vector<CoinAnim>();
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
	public float              lastPosX = Total._getX(319);
	public float              lastPosY = Total._getY(534);
	
	public boolean            m_bDrawRuleLine;   
	
	public boolean            m_bIsAnim;
	
	public boolean            m_bIncrease;
	public boolean            m_ArrRectState;
	 
 	
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		scene.addChild(new GameLayer());
		return scene;
	}
/***************************************************CONSTRACTOR*******************************************************************************************************************************************************/	
	public GameLayer()
	{
		super();	
		
		m_Eng = new Engine();
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
		m_Eng.setCardBettwenY(Total.CARD_BETWEEN_Y);
		
	}
/***************************************************LOAD IMAGES*******************************************************************************************************************************************************/
	public void initImages(){
		int nCurStage = Total.curLevel;
		CCSprite im_back = CCSprite.sprite(Total._getImg(String.format("backImages/game_bg%d-hd",nCurStage)));
		Total.setScale(im_back);
		im_back.setAnchorPoint(0, 0);
		im_back.setPosition(0, 0);
		addChild(im_back);
		
		for(int i = 0; i < Total.CHARACTER_COUNT ; i++){
			m_sprCharacter[i] = CCSprite.sprite(Total._getImg(String.format("character/stage%d/%s",  Total.curLevel, Total.strIconName[i])));
			Total.setScale(m_sprCharacter[i]);
			m_sprCharacter[i].setAnchorPoint(0, 0);
		}
		for(int i = 0; i < Total.RULE_LINE_COUNT ; i++){
			m_arrLine[i] = CCSprite.sprite(Total._getImg(String.format("lines/line%d", i + 1)));
			Total.setScale(m_arrLine[i]);
			m_arrLine[i].setAnchorPoint(0, 0);
			addChild(m_arrLine[i]);
			m_arrLine[i].setPosition(Total._getX(Total.lineX), Total._getY(Total.lineY[i]));
			m_arrLine[i].setVisible(false);			
		}
		for(int i = 0; i < Total.COL_ ; i++){
			m_arrHold[i] = CCSprite.sprite(Total._getImg("Buttons/hold"));
			Total.setScale(m_arrHold[i]);
			m_arrHold[i].setAnchorPoint(0, 0);
			m_arrHold[i].setPosition(Total._getX(116 + i * 146), Total._getY(125));
			this.addChild(m_arrHold[i],z_hold);		
		}
	}
/***************************************************BUTTONS LOAD*******************************************************************************************************************************************************/
	public void initButton(){
		GrowButton back = GrowButton.button(Total._getImg("Buttons/back1"), Total._getImg("Buttons/back2"),this,"onBack",0);
		GrowButton coin = GrowButton.button(Total._getImg("Buttons/addCoin1"), Total._getImg("Buttons/addCoin2"),this,"onCoinBuy",0);
		//GrowButton paytable = GrowButton.button(Total._getImg("Buttons/paytable1"), Total._getImg("Buttons/paytable2"),this,"onPlayTable",0);
		GrowButton line = GrowButton.button(Total._getImg("Buttons/line1"), Total._getImg("Buttons/line2"),this,"onLines",0);
		GrowButton maxline = GrowButton.button(Total._getImg("Buttons/maxlines1"), Total._getImg("Buttons/maxlines2"),this,"onMaxLines",0);
		GrowButton bet = GrowButton.button(Total._getImg("Buttons/bet1"), Total._getImg("Buttons/bet2"),this,"onBet",0);
		GrowButton spin = GrowButton.button(Total._getImg("Buttons/spin1"), Total._getImg("Buttons/spin1"),this,"onSpin",0);
		GrowButton setting = GrowButton.button(Total._getImg("Buttons/setting1"), Total._getImg("Buttons/setting2"), this, "setting",0);
		setting.setAnchorPoint(0, 0);
		setting.setPosition(Total._getX(50), Total._getY(586));
		
		back.setPosition(Total._getX(908), Total._getY(602));
		coin.setPosition(Total._getX(76), Total._getY(58));
		//paytable.setPosition(Total._getX(76),Total._getY(58));
		if(Total.curLevel == 1 || Total.curLevel == 3){
			line.setPosition(Total._getX(232), Total._getY(34));
			maxline.setPosition(Total._getX(431), Total._getY(34));
			bet.setPosition(Total._getX(630), Total._getY(34));
			spin.setPosition(Total._getX(908), Total._getY(55));
		}else if(Total.curLevel == 2){
			line.setPosition(Total._getX(255), Total._getY(34));
			maxline.setPosition(Total._getX(436), Total._getY(34));
			bet.setPosition(Total._getX(612), Total._getY(34));
			spin.setPosition(Total._getX(908), Total._getY(55));
		}else if(Total.curLevel == 4){
			line.setPosition(Total._getX(254), Total._getY(34));
			maxline.setPosition(Total._getX(427), Total._getY(34));
			bet.setPosition(Total._getX(610), Total._getY(34));
			spin.setPosition(Total._getX(860), Total._getY(55));
		}else if(Total.curLevel == 5){
			line.setPosition(Total._getX(252), Total._getY(34));
			maxline.setPosition(Total._getX(427), Total._getY(34));
			bet.setPosition(Total._getX(609), Total._getY(34));
			spin.setPosition(Total._getX(870), Total._getY(55));
		}else if(Total.curLevel == 6){
			line.setPosition(Total._getX(252), Total._getY(34));
			maxline.setPosition(Total._getX(431), Total._getY(34));
			bet.setPosition(Total._getX(610), Total._getY(34));
			spin.setPosition(Total._getX(860), Total._getY(55));
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
		m_lblCoin = CCLabel.makeLabel(String.format("%d", m_Eng.m_nGameCoin), Total._getFont("Imagica"), 40);
		Total.setScale(m_lblCoin);
		m_lblCoin.setAnchorPoint(0, 0);
		m_lblCoin.setPosition(Total._getX(200), Total._getY(544));
		m_lblCoin.setColor(clr);
		addChild(m_lblCoin);
		
		m_lblLines = CCLabel.makeLabel(String.format("%d", m_Eng.m_nRuleLineCount), Total._getFont("Imagica"), 30);
		Total.setScale(m_lblLines);
		m_lblLines.setAnchorPoint(0, 0);
		m_lblLines.setPosition(Total._getX(225), Total._getY(65));
		m_lblLines.setColor(clr);
		addChild(m_lblLines);
		
		m_lblMaxLines = CCLabel.makeLabel(String.format("%d", m_Eng.m_nMaxLineCount), Total._getFont("Imagica"), 30);
		Total.setScale(m_lblMaxLines);
		m_lblMaxLines.setAnchorPoint(0, 0);
		m_lblMaxLines.setPosition(Total._getX(421), Total._getY(65));
		m_lblMaxLines.setColor(clr);
		addChild(m_lblMaxLines);
		
		m_lblBets = CCLabel.makeLabel(String.format("%d",m_Eng.m_nBet), Total._getFont("Imagica"), 30);
		Total.setScale(m_lblBets);
		m_lblBets.setAnchorPoint(0, 0);
		m_lblBets.setPosition(Total._getX(615), Total._getY(65));
		m_lblBets.setColor(clr);
		addChild(m_lblBets);
		
		m_lblWin = CCLabel.makeLabel(String.format("%d",m_Eng.m_nWin), Total._getFont("Imagica"), 30);
		Total.setScale(m_lblWin);
		m_lblWin.setAnchorPoint(0, 0);
		if(Total.curLevel == 4 || Total.curLevel == 5 || Total.curLevel ==6)
			m_lblWin.setPosition(Total._getX(760), Total._getY(25));
		else
			m_lblWin.setPosition(Total._getX(800), Total._getY(25));
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
		Total.TGameResult.clear();
		drawLine(false);
		for(int i = 0; i < Total.COL_ ; i++)
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
			if(m_nSlotTick > 2 * Total.TICK){
				int nCols = m_nSlotTick / Total.TICK -1;
				if(m_nSlotTick % 10 == 0){
					if(nCols < 6)
						m_Eng.m_strState[nCols - 1] = "last";
					else if(m_Eng.m_bSloting[Total.COL_ - 1] == false)
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
		float nCardX = Total.CARD_STRT_X ;
	    float nCardY = Total.CARD_STRT_Y;
	    float nCardBetweenX = Total.CARD_BETWEEN_X;
	    float nCardBetweenY = Total.CARD_BETWEEN_Y;
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
	        if( (Total.TGameResult.size() > 0) && !m_bIsAnim ){
	            m_bIsAnim= true;
	           for(int i = 0; i < Total.TGameResult.size() ; i++){
	        	   int nRuleIndex = Total.TGameResult.get(i).nRuleLineIndex;
	        	   int nEqualCount = Total.TGameResult.get(i).nEqualCount;
	        	   //int nCharIndex = Total.TGameResult.get(i).nCharacterIndex;
	        	   m_arrLine[nRuleIndex].setVisible(true);
	        	   for(int j = 0 ; j < nEqualCount ; j++){
	        		   CGPoint ptCardPos = new CGPoint();
	        		   ptCardPos.set(Total._getX(nCardX + m_Eng.nArrRules[nRuleIndex][j][1] * nCardBetweenX),
	        				   Total._getY(nCardY - (m_Eng.nArrRules[nRuleIndex][j][0] - 1) * nCardBetweenY));
	        		   CCSprite rect = CCSprite.sprite(Total._getImg("Buttons/rect"));
	        	       Total.setScale(rect);
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
		gl.glLineWidth(Total._getX(0.6f));
		float nCardX = Total.CARD_STRT_X;
	    float nCardY = Total.CARD_STRT_Y;
	    float nCardBetweenX = Total.CARD_BETWEEN_X;
	    float nCardBetweenY = Total.CARD_BETWEEN_Y;
	    for(int i = 0; i < Total.ROW_; i++){
	        for(int j = 0; j < Total.COL_; j++){
	            int nCardType = m_Eng.m_nArrSlot[i][j];
	            CCSprite spr = m_sprCharacter[nCardType];
	            spr.setPosition(Total._getX(nCardX) + j * Total._getX(nCardBetweenX), Total._getY(nCardY) - (i - 1)  * Total._getY(nCardBetweenY) - m_Eng.m_fMovingY[j]);
	            spr.visit(gl);
	            
	        }
	    }    
	}
/*********************************************************************************DRAW HOLDS**************************************************************************************************************************/
	public void drawHolds(){
		for (int i = 0; i < Total.COL_; i++) {
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
		    for(int i = 0; i < Total.RULE_LINE_COUNT; i++){
		        if (i <  m_Eng.m_nRuleLineCount)
		            m_arrLine[i].setVisible(bDrawRuleLine) ;
		        else 
		        	m_arrLine[i].setVisible(false);
		    }
		
	}
/*********************************************************************************COIN ANIMATIONS**************************************************************************************************************************/
	public void coinAnim(float dt){
		if(arrCoin.size() < 15){
			CoinAnim coin = new CoinAnim();
			addChild(coin, z_coin, tag_coin);
			coin.setPosition(CCDirector.sharedDirector().winSize().width / 2, Total._getY(90));
			arrCoin.add(coin);
		}else if(arrCoin.get(14).getPosition().y > CCDirector.sharedDirector().winSize().height - Total._getY(40)){
			arrCoin.clear();
			unschedule("coinAnim");
		}
	}
/******************************************************************SET INFO**************************************************************************************************************************/
	public void setInfo(){
		Total.allCoin = m_Eng.m_nGameCoin;
		Total.curLine = m_Eng.m_nRuleLineCount;
		Total.maxline = m_Eng.m_nMaxLineCount;
		Total.bet = m_Eng.m_nBet;
		Total.saveSetting();
	}
/*********************************************************************************BUTTONS DEFINE**************************************************************************************************************************/
	public void onBack(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		Total.titleState = false;
		setInfo();			
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, TitleLayer.scene()));
	}
	
	public void onCoinBuy(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.GAME_STATE = "game";
		Total.playEffect(Total.click);
		setInfo();
		Total.payTableFlag = true;
		for(int i = 0; i < Total.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Total.COL_ ; j++){
				if(i == 0){
					Total.rowIndex[i] = m_Eng.m_nRowIndex[i];
				}
				if(i < Total.ROW_)
					Total.arrSlot[i][j] = m_Eng.m_nArrSlot[i][j];
				Total.arrTempSlot[i][j] = m_Eng.m_nArrTempSlot[i][j];
			}
		}		
		//if (VunglePub.isVideoAvailable(true))
		//	VunglePub.displayIncentivizedAdvert(true);	
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
		
	}
	public void onPlayTable(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		setInfo();
		Total.payTableFlag = true;
		for(int i = 0; i < Total.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Total.COL_ ; j++){
				if(i == 0){
					Total.rowIndex[i] = m_Eng.m_nRowIndex[i];
				}
				if(i < Total.ROW_)
					Total.arrSlot[i][j] = m_Eng.m_nArrSlot[i][j];
				Total.arrTempSlot[i][j] = m_Eng.m_nArrTempSlot[i][j];
			}
		}		
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, PayTable.scene()));
	}
	public void onLines(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		m_Eng.m_nRuleLineCount++;
		if(m_Eng.m_nRuleLineCount > m_Eng.m_nMaxLineCount)
			m_Eng.m_nRuleLineCount = 1;
		m_lblLines.setString(String.format("%d", m_Eng.m_nRuleLineCount));
		drawLine(true);
	}
	public void onMaxLines(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		m_Eng.m_nMaxLineCount++;
		if (m_Eng.m_nMaxLineCount > Total.RULE_LINE_COUNT)
			m_Eng.m_nMaxLineCount = 1;			
		m_Eng.m_nRuleLineCount = m_Eng.m_nMaxLineCount;
		m_lblLines.setString(String.format("%d", m_Eng.m_nRuleLineCount));
		m_lblMaxLines.setString(String.format("%d", m_Eng.m_nMaxLineCount));
		drawLine(true);
		
	}
	
	public void onBet(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		m_Eng.m_nBet++;
		if(m_Eng.m_nBet > 10)
			m_Eng.m_nBet = 1;
		m_lblBets.setString(String.format("%d", m_Eng.m_nBet));
	}
	public void onSpin(Object sender){
		if(m_Eng.m_bStartSlot || m_bIncrease)
			return;
		Total.playEffect(Total.click);
		startSlot();	
			
	}
/***************************************************************************************************************************************************************************************************************/
	public void setting(Object sender){
		Total.playEffect(Total.click);
		Total.titleState = true;
		Total.GAME_STATE = "game";
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, Setting.scene()));
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
									CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.7f, CoinBuy.scene()));
									Total.GAME_STATE = "game";
									dialog.cancel();
	                             }
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {								
									if(Total.allCoin <= 0 && !Total.setTimeState){
										Total.currentTime = System.currentTimeMillis() / 1000 ;
										Total.setTimeState = true;
										Total.saveSetting();
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