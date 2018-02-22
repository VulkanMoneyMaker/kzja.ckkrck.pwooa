package gak.hafawq.nva.DataLayer;


import org.cocos2d.utils.javolution.MathLib;

import gak.hafawq.nva.ui.Resources;

public class ItemEngine
{	
	public int m_nArrSlot[][] = new int[Resources.ROW_][Resources.COL_];
	public int m_nArrTempSlot[][] = new int[Resources.CHARACTER_COUNT][Resources.COL_];
	public float m_fBetweenY;
	public float m_fMovingY[] = new float[Resources.COL_];
	public boolean m_bSloting[] = new boolean[Resources.COL_];
	public int m_nRuleLineCount;
	public int[] m_nRowIndex = new int[Resources.COL_];
	public int m_nMaxLineCount;

	public int m_nBet;
	public int m_nWin;
	public int m_nGameCoin;
	public boolean m_bStartSlot;
	public boolean m_bHit;
	public boolean m_bLoad;
	
	public float[] m_fPrevStep = new float[Resources.COL_];
	public int[] m_fMovingYStep = new int[Resources.COL_];
	public int[] m_nSlotTick = new int[Resources.COL_];
	public String[] m_strState = new String[Resources.COL_];
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
	public ItemEngine()
	{
		initVariable();
		initSlot();
		
	}
	public void getInfo(){
		m_nGameCoin = Resources.allCoin;
		m_nRuleLineCount = Resources.curLine;
		m_nMaxLineCount = Resources.maxline;
		m_nBet = Resources.bet;
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
		int nPrevStep = Resources.PREVSTEP;
		for(int i = 0; i < Resources.COL_ ; i++){
			initCardStartPosY(i);
			m_bSloting[i] = false;
			m_fMovingYStep[i] = Resources.MIN_VEL;
			m_fPrevStep[i] = - (MathLib.random(1, nPrevStep - 1) + 2);
		}
	}
	public void loadSlot(){
		m_bStartSlot = true;
		m_bLoad = true;
		for(int i = 0; i < Resources.CHARACTER_COUNT ; i++){
			for(int j = 0; j < Resources.COL_ ; j++){
				if(i == 0)
					m_nRowIndex[i] = Resources.rowIndex[i];
				if(i < Resources.ROW_)
					m_nArrSlot[i][j] = Resources.arrSlot[i][j];
				m_nArrTempSlot[i][j] = Resources.arrTempSlot[i][j];
			}
		}
		Resources.payTableFlag = false;
	}
	public void initSlot(){
		int changeVal= 0;
		if(Resources.payTableFlag){
			loadSlot();
			return;
		}
		for (int i = 0; i < Resources.CHARACTER_COUNT; i++) {
	        for (int j = 0; j < Resources.COL_; j++) {
	            m_nArrTempSlot[i][j] = -1;
	        }
	    }
		for(int i = 0; i < Resources.COL_ ; i++){
			for(int j = 0; j < Resources.CHARACTER_COUNT ; j++){
				boolean bFlag = true;
				while(bFlag){
					changeVal = MathLib.random(0, (Resources.CHARACTER_COUNT - 1));
					boolean equalState = true;
					for(int k = 0; k < Resources.CHARACTER_COUNT ; k++){
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
	    
	    for (int i = 0; i < Resources.ROW_; i++) {
	        for (int j = 0; j < Resources.COL_; j++)
	            m_nArrSlot[i][j] = m_nArrTempSlot[i][j];
	    }
	}
	public void setCardBettwenY(float fBetweenY){
		m_fBetweenY = fBetweenY;
	}
	public void resetSlot(int nColIndex){
		if(m_fMovingY[nColIndex] >= m_fBetweenY){
			for(int j = Resources.ROW_ - 2; j >= 0 ; j--){
				m_nArrSlot[j + 1][nColIndex] = m_nArrSlot[j][nColIndex];				
			}
			m_nArrSlot[0][nColIndex] = m_nArrTempSlot[m_nRowIndex[nColIndex]][nColIndex];
			m_nRowIndex[nColIndex]--;
			if(m_nRowIndex[nColIndex] < 0)
				m_nRowIndex[nColIndex] = Resources.CHARACTER_COUNT - 1;
			Resources.playEffect(Resources.spin);
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
			if(m_strState[nColIndex].equals("normal") && m_fMovingYStep[nColIndex] < Resources.MAX_VEL)
				m_fMovingYStep[nColIndex] += 1;
			if(m_strState[nColIndex].equals("last")){
				if(m_fMovingYStep[nColIndex]> Resources.MIN_VEL){
					m_fMovingYStep[nColIndex] -= 1;
					if(m_fMovingYStep[nColIndex] < Resources.MIN_VEL)
						m_fMovingYStep[nColIndex] = Resources.MIN_VEL;
				}
			}		
			m_fMovingY[nColIndex] += m_fMovingYStep[nColIndex];
		}
	}
	public void setState(int tick, int nColIndex){
		if(tick < Resources.PREVTICK)
			m_strState[nColIndex] = "prev";
		else if(!m_strState[nColIndex].equals("last"))
			m_strState[nColIndex] = "normal";
		if(m_bSloting[nColIndex] && m_strState[nColIndex].equals("last")){
			if(m_nSlotTick[nColIndex] < Resources.TICK)
				m_nSlotTick[nColIndex]++;
			else{
				m_bSloting[nColIndex] = false;
				m_nSlotTick[nColIndex] = 0;
			}
		}
			
	}
	public void processSlot(int tick){
		for(int i = 0; i < Resources.COL_ ; i++){
			if(m_fMovingY[i] == 0 && !m_bSloting[i])
				continue;
			setState(tick, i);
			moveSlot(i);
			resetSlot(i);
		}
	}
	public boolean isStopAllSlots(){
		boolean bIsStop = true;
		for(int i = 0; i < Resources.COL_ ; i++)
			bIsStop &= !m_bSloting[i];
		return bIsStop;
	}
	public void compareCards(){
		int nPrevStep = Resources.PREVSTEP;
		for(int i = 0; i < Resources.COL_ ; i++){
			m_fMovingYStep[i] = Resources.MIN_VEL;
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
			for(int nEqualIndex = 0; nEqualIndex < Resources.COL_ - 1 ; nEqualIndex++){
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
				ResultGaming r = new ResultGaming();
				r.nRuleLineIndex = nRuleLineIndex;
				r.nEqualCount = nEqualCount;
				r.nCharacterIndex = nCardType;
				Resources.TGameResult.add(r);
				m_nWin += nCoin * m_nBet;				
				Resources.playEffect(Resources.seccess);
				nEqualCount = 1;
				nCardType = 1;
			}
		}
		if(m_bHit){
			m_nGameCoin += m_nWin;
			
		}else if(!m_bHit){
			Resources.playEffect(Resources.fire_btn);
			m_nGameCoin -= m_nBet*m_nRuleLineCount;			
		}
		Resources.allCoin = m_nGameCoin;
		Resources.saveSetting();
	}
	
	
	
	
	
	
}