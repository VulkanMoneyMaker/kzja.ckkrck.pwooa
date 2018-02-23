package com.slotsonline.goappru.layouts;

///import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.utils.javolution.MathLib;

import com.slotsonline.goappru.activities.Data;


public class LayoutAnimations extends CCSprite{
	public float m_fGravity;
	public float m_fVx;
	public float m_fVy;
	public int m_nTime;
	
	
/***************************************************************************************************************************************************************************************************************/
	public LayoutAnimations(){
		super(Data._getImg("Buttons/usd3"));
		Data.setScale(this);
		initVar();	
		schedule("firstAction", 1.0f / 60.0f);		
	}
/***************************************************************************************************************************************************************************************************************/
	public void initVar(){
		float nVelXRes = Data._getX(3.0f);
		float nVelYRes = Data._getY(5.0f);
		m_fGravity = Data._getX(3.0f);
		m_fVx = Data._getX(20.0f) + MathLib.random(0, nVelXRes);
		m_fVy = Data._getX(20.0f) + MathLib.random(0, nVelYRes);
		m_nTime = 0;
		setScale(0.1f);
	}		
/***************************************************************************************************************************************************************************************************************/
	public void firstAction(float dt){
		m_nTime++;
		m_fVy -= m_fGravity;
		setPosition(getPosition().x + m_fVx, getPosition().y + m_fVy);
		setScale(getScale() + 0.04f);
		if(getPosition().y < Data._getY(90)){
			m_nTime = 0 ;
			m_fVy = -1.5f * m_fVy;
			m_fVx = -(getPosition().x - Data._getX(250f)) / ((CCDirector.sharedDirector().winSize().height - Data._getY(40.0f)) / m_fVy);
			unschedule("firstAction");
			schedule("restAction", 1.0f / 60.0f);
		}
	}
/***************************************************************************************************************************************************************************************************************/
	public void restAction(float dt){
		m_nTime++;
		if(m_nTime > 5){
			unschedule("restAction");
			schedule("secondAction", 1.0f / 60.0f);
		}
	}
/***************************************************************************************************************************************************************************************************************/
	public void secondAction(float dt){
		setPosition(this.getPosition().x + m_fVx, getPosition().y + m_fVy);
		if(getPosition().y > CCDirector.sharedDirector().winSize().height - Data._getY(40)){
			setVisible(false);
			unschedule("secondAction");
		}
	}
	
	
}
