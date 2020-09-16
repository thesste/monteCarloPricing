/**
 * 
 */
package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thesste
 *
 */
public class CalculateDiscountedPayoffTest {

	/**
	 * Test method for {@link monteCarloPricing.MCPricer#calculateDiscountedPayoff(double, double)}.
	 */
	@Test
	public void testCalculateDiscountedPayoff() {
		
		int numberOfPaths = 1;
		double maxMaturity = 1;
		double underlyingPrice = 100;
		double volatility = 0.2;
		double riskFreeRate = 0.05;
		int numberOfTradingDaysPerYear = 1;
		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		
		double discountedPayoff = pricer.calculateDiscountedPayoff(100, 2);	
		assertEquals(90.48, discountedPayoff, 0.05);
		
	}

}
