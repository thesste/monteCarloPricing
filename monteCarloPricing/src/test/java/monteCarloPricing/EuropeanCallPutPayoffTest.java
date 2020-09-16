package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thesste
 *
 */
public class EuropeanCallPutPayoffTest {

	/**
	 * Test method for {@link monteCarloPricing.EuropeanCallPut#payoff(double[])}.
	 */
	@Test
	public void testPayoff() {

		double[] underlyingPath1 = {100, 90};
		double[] underlyingPath2 = {100, 105.5};

		EuropeanCallPut putTest = new EuropeanCallPut(100, "Put");
		assertEquals(10, putTest.payoff(underlyingPath1), 0);		
		assertEquals(0, putTest.payoff(underlyingPath2), 0);

		EuropeanCallPut callTest = new EuropeanCallPut(100, "Call");
		assertEquals(0, callTest.payoff(underlyingPath1), 0);		
		assertEquals(5.5, callTest.payoff(underlyingPath2), 0);		
	}

}
