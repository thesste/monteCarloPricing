package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thesste
 *
 */
public class BarrierCallPutPayoffTest {

	/**
	 * Test method for {@link monteCarloPricing.KnockInOutBarrierCallPut#payoff(double[])}.
	 */
	@Test
	public void testPayoff() {

		double[] underlyingPath1 = {100, 97, 130};
		double[] underlyingPath2 = {100, 122.5, 92};
		
		KnockInOutBarrierCallPut putTest = new KnockInOutBarrierCallPut(100, 120, "Put", "UpIn");
		assertEquals(0, putTest.payoff(underlyingPath1), 0);		
		assertEquals(8, putTest.payoff(underlyingPath2), 0);
		
		KnockInOutBarrierCallPut callTest = new KnockInOutBarrierCallPut(100, 95.5, "Call", "DownOut");		
		assertEquals(30, callTest.payoff(underlyingPath1), 0);		
		assertEquals(0, callTest.payoff(underlyingPath2), 0);	
	}

}
