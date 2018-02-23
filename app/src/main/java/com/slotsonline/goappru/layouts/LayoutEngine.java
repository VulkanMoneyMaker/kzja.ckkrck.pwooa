package com.slotsonline.goappru.layouts;


import org.cocos2d.utils.javolution.MathLib;

import com.slotsonline.goappru.activities.Data;

public class LayoutEngine
{	
	public int m_nArrSlot[][] = new int[Data.ROW_][Data.COL_];
	public int m_nArrTempSlot[][] = new int[Data.CHARACTER_COUNT][Data.COL_];
	public float m_fBetweenY;
	public float m_fMovingY[] = new float[Data.COL_];
	public boolean m_bSloting[] = new boolean[Data.COL_];
	public int m_nRuleLineCount;
	public int[] m_nRowIndex = new int[Data.COL_];
	public int m_nMaxLineCount;
	//public int nRuleLineIndex;
	public int m_nBet;
	public int m_nWin;
	public int m_nGameCoin;
	public boolean m_bStartSlot;
	public boolean m_bHit;
	public boolean m_bLoad;
	
	public float[] m_fPrevStep = new float[Data.COL_];
	public int[] m_fMovingYStep = new int[Data.COL_];
	public int[] m_nSlotTick = new int[Data.COL_];
	public String[] m_strState = new String[Data.COL_];
	public int[][] nCardsScore = {
		{0,5,50,500,5000},
		{0,0,5,25,50},
		{0,2,40,200,1000},
		{0,2,30,150,500},
		{0,2,25,100,300},
		{0,0,20,75,200},
		{0,0,20,75,200},
		{0,0,15,50,100},
		{0,0,15,50,100},
		{0,0,10,25,50},
		{0,0,10,25,50}
	};
	
	public int[][][] nArrRules = {
		{{2,0},{2,1},{2,2},{2,3},{2,4}},
		{{1,0},{1,1},{1,2},{1,3},{1,4}},
		{{3,0},{3,1},{3,2},{3,3},{3,4}},
		{{1,0},{2,1},{3,2},{2,3},{1,4}},
		{{3,0},{2,1},{1,2},{2,3},{3,4}},
		{{2,0},{1,1},{1,2},{1,3},{2,4}},
		{{2,0},{3,1},{3,2},{3,3},{2,4}},
		{{1,0},{2,1},{2,2},{2,3},{1,4}},
		{{3,0},{2,1},{2,2},{2,3},{3,4}}		
	};	
	public LayoutEngine()
	{
		initVariable();
		initSlot();
		
	}
	public void getInfo(){
		m_nGameCoin = Data.allCoin;
		m_nRuleLineCount = Data.curLine;
		m_nMaxLineCount = Data.maxline;
		m_nBet = Data.bet;
	}
	public void initCardStartPosY(int nColIndex){
		if(m_bSloting[nColIndex])
			m_fMovingY[nColIndex] -= m_fBetweenY;
		else
			m_fMovingY[nColIndex] = 0;
	}
	public void initVariable(){
		m_bStartSlot = false;
		getInfo();
		int nPrevStep = Data.PREVSTEP;
		for(int i = 0; i < Data.COL_ ; i++){
			initCardStartPosY(i);
			m_bSloting[i] = false;
			m_fMovingYStep[i] = Data.MIN_VEL;
			m_fPrevStep[i] = - (MathLib.random(1, nPrevStep - 1) + 2);
		}
	}
	public void loadSlot(){
		m_bStartSlot = true;
		m_bLoad = true;
		for(int i = 0; i < Data.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Data.COL_ ; j++){
				if(i == 0)
					m_nRowIndex[i] = Data.rowIndex[i];
				if(i < Data.ROW_)
					m_nArrSlot[i][j] = Data.arrSlot[i][j];
				m_nArrTempSlot[i][j] = Data.arrTempSlot[i][j];
			}
		}
		Data.payTableFlag = false;
	}
	public void initSlot(){
		int changeVal= 0;
		if(Data.payTableFlag){
			loadSlot();
			return;
		}
		for (int i = 0; i < Data.CHARACTER_COUNT; i++) {
	        for (int j = 0; j < Data.COL_; j++) {
	            m_nArrTempSlot[i][j] = -1;
	        }
	    }
		for(int i = 0; i < Data.COL_ ; i++){
			for(int j = 0; j < Data.CHARACTER_COUNT ; j++){
				boolean bFlag = true;
				while(bFlag){
					changeVal = MathLib.random(0, (Data.CHARACTER_COUNT - 1));
					boolean equalState = true;
					for(int k = 0; k < Data.CHARACTER_COUNT ; k++){
						if(m_nArrTempSlot[k][i] == changeVal){
							equalState = false;
							break;
						}
					}
					if(equalState){
						m_nArrTempSlot[j][i] = changeVal;
						bFlag = false;
					}
				}
				
			}
		}
	    
	    for (int i = 0; i < Data.ROW_; i++) {
	        for (int j = 0; j < Data.COL_; j++)
	            m_nArrSlot[i][j] = m_nArrTempSlot[i][j];
	    }
	}
	public void setCardBettwenY(float fBetweenY){
		m_fBetweenY = fBetweenY;
	}
	public void resetSlot(int nColIndex){
		if(m_fMovingY[nColIndex] >= m_fBetweenY){
			for(int j = Data.ROW_ - 2; j >= 0 ; j--){
				m_nArrSlot[j + 1][nColIndex] = m_nArrSlot[j][nColIndex];				
			}
			m_nArrSlot[0][nColIndex] = m_nArrTempSlot[m_nRowIndex[nColIndex]][nColIndex];
			m_nRowIndex[nColIndex]--;
			if(m_nRowIndex[nColIndex] < 0)
				m_nRowIndex[nColIndex] = Data.CHARACTER_COUNT - 1;
			Data.playEffect(Data.spin);
			initCardStartPosY(nColIndex);
		}
	}
	public void moveSlot(int nColIndex){
		if(m_strState[nColIndex].equals("prev")){
			m_fMovingY[nColIndex] += m_fPrevStep[nColIndex];
			if(m_fPrevStep[nColIndex] <= 0.2f)
				m_fPrevStep[nColIndex] /= 1.3f;
			else
				m_fPrevStep[nColIndex] = 0;			
		}else{
			if(m_strState[nColIndex].equals("normal") && m_fMovingYStep[nColIndex] < Data.MAX_VEL)
				m_fMovingYStep[nColIndex] += 1;
			if(m_strState[nColIndex].equals("last")){
				if(m_fMovingYStep[nColIndex]> Data.MIN_VEL){
					m_fMovingYStep[nColIndex] -= 1;
					if(m_fMovingYStep[nColIndex] < Data.MIN_VEL)
						m_fMovingYStep[nColIndex] = Data.MIN_VEL;
				}
			}		
			m_fMovingY[nColIndex] += m_fMovingYStep[nColIndex];
		}
	}
	public void setState(int tick, int nColIndex){
		if(tick < Data.PREVTICK)
			m_strState[nColIndex] = "prev";
		else if(!m_strState[nColIndex].equals("last"))
			m_strState[nColIndex] = "normal";
		if(m_bSloting[nColIndex] && m_strState[nColIndex].equals("last")){
			if(m_nSlotTick[nColIndex] < Data.TICK)
				m_nSlotTick[nColIndex]++;
			else{
				m_bSloting[nColIndex] = false;
				m_nSlotTick[nColIndex] = 0;
			}
		}
			
	}
	public void processSlot(int tick){
		for(int i = 0; i < Data.COL_ ; i++){
			if(m_fMovingY[i] == 0 && !m_bSloting[i])
				continue;
			setState(tick, i);
			moveSlot(i);
			resetSlot(i);
		}
	}
	public boolean isStopAllSlots(){
		boolean bIsStop = true;
		for(int i = 0; i < Data.COL_ ; i++)
			bIsStop &= !m_bSloting[i];
		return bIsStop;
	}
	public void compareCards(){
		int nPrevStep = Data.PREVSTEP;
		for(int i = 0; i < Data.COL_ ; i++){
			m_fMovingYStep[i] = Data.MIN_VEL;
			m_fPrevStep[i] = -(MathLib.random(0, nPrevStep - 1) + 5);
		}
		m_bHit = false;
		m_bStartSlot = false;
		if(m_bLoad){
			m_bLoad = false;
			return;
		}
		int nCardType = -1;
		int nEqualCount = 1;
		for(int nRuleLineIndex = 0 ; nRuleLineIndex < m_nRuleLineCount ; nRuleLineIndex++){			
			for(int nEqualIndex = 0; nEqualIndex < Data.COL_ - 1 ; nEqualIndex++){
				int nFirstType = m_nArrSlot[nArrRules[nRuleLineIndex][nEqualIndex][0]][nArrRules[nRuleLineIndex][nEqualIndex][1]];
				int nSecondType = m_nArrSlot[nArrRules[nRuleLineIndex][nEqualIndex + 1][0]][nArrRules[nRuleLineIndex][nEqualIndex + 1][1]];
				if(nFirstType == nSecondType){
					nCardType = nFirstType;
					nEqualCount = nEqualIndex + 2;
								
				}else
					break;
			}	
			int nCoin = 0;
			if(nCardType > -1)
				nCoin = nCardsScore[nCardType][nEqualCount - 1];
			if(nCoin > 0){
				m_bHit = true;
				LayoutResult r = new LayoutResult();
				r.nRuleLineIndex = nRuleLineIndex;
				r.nEqualCount = nEqualCount;
				r.nCharacterIndex = nCardType;
				Data.TGameResult.add(r);
				m_nWin += nCoin * m_nBet;				
				Data.playEffect(Data.seccess);
				nEqualCount = 1;
				nCardType = 1;
			}
		}
		if(m_bHit){
			m_nGameCoin += m_nWin;
			
		}else if(!m_bHit){
			Data.playEffect(Data.fire_btn);
			m_nGameCoin -= m_nBet*m_nRuleLineCount;			
		}
		Data.allCoin = m_nGameCoin;
		Data.saveSetting();
	}
	
	
	
	
	
	
}