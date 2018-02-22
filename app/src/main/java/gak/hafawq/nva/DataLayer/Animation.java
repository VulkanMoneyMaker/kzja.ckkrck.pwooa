package gak.hafawq.nva.DataLayer;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.utils.javolution.MathLib;

import gak.hafawq.nva.ui.Resources;


public class Animation extends CCSprite{
	public float m_fGravity;
	public float m_fVx;
	public float m_fVy;
	public int m_nTime;
	
	
/***************************************************************************************************************************************************************************************************************/
	public Animation(){
		super(Resources._getImg("Buttons/usd3"));
		Resources.setScale(this);
		initVar();	
		schedule("firstAction", 1.0f / 60.0f);		
	}
/***************************************************************************************************************************************************************************************************************/
	public void initVar(){
		float nVelXRes = Resources._getX(3.0f);
		float nVelYRes = Resources._getY(5.0f);
		m_fGravity = Resources._getX(3.0f);
		m_fVx = Resources._getX(20.0f) + MathLib.random(0, nVelXRes);
		m_fVy = Resources._getX(20.0f) + MathLib.random(0, nVelYRes);
		m_nTime = 0;
		setScale(0.1f);
	}		
/***************************************************************************************************************************************************************************************************************/
	public void firstAction(float dt){
		m_nTime++;
		m_fVy -= m_fGravity;
		setPosition(getPosition().x + m_fVx, getPosition().y + m_fVy);
		setScale(getScale() + 0.04f);
		if(getPosition().y < Resources._getY(90)){
			m_nTime = 0 ;
			m_fVy = -1.5f * m_fVy;
			m_fVx = -(getPosition().x - Resources._getX(250f)) / ((CCDirector.sharedDirector().winSize().height - Resources._getY(40.0f)) / m_fVy);
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
		if(getPosition().y > CCDirector.sharedDirector().winSize().height - Resources._getY(40)){
			setVisible(false);
			unschedule("secondAction");
		}
	}
	
	
}
