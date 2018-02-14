package hfj.pwoei.prihdk.Layer;

///import org.cocos2d.nodes.CCDirector;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.utils.javolution.MathLib;

import hfj.pwoei.prihdk.slotmania.G;


public class CoinAnim extends CCSprite{
	public float m_fGravity;
	public float m_fVx;
	public float m_fVy;
	public int m_nTime;
	
	
/***************************************************************************************************************************************************************************************************************/
	public CoinAnim(){
		super(G._getImg("Buttons/usd3"));
		G.setScale(this);
		initVar();	
		schedule("firstAction", 1.0f / 60.0f);		
	}
/***************************************************************************************************************************************************************************************************************/
	public void initVar(){
		float nVelXRes = G._getX(3.0f);
		float nVelYRes = G._getY(5.0f);
		m_fGravity = G._getX(3.0f);
		m_fVx = G._getX(20.0f) + MathLib.random(0, nVelXRes);
		m_fVy = G._getX(20.0f) + MathLib.random(0, nVelYRes);
		m_nTime = 0;
		setScale(0.1f);
	}		
/***************************************************************************************************************************************************************************************************************/
	public void firstAction(float dt){
		m_nTime++;
		m_fVy -= m_fGravity;
		setPosition(getPosition().x + m_fVx, getPosition().y + m_fVy);
		setScale(getScale() + 0.04f);
		if(getPosition().y < G._getY(90)){
			m_nTime = 0 ;
			m_fVy = -1.5f * m_fVy;
			m_fVx = -(getPosition().x - G._getX(250f)) / ((CCDirector.sharedDirector().winSize().height - G._getY(40.0f)) / m_fVy);
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
		if(getPosition().y > CCDirector.sharedDirector().winSize().height - G._getY(40)){
			setVisible(false);
			unschedule("secondAction");
		}
	}
	
	
}
