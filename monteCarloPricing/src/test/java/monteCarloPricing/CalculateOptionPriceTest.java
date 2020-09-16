package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;
import net.finmath.functions.AnalyticFormulas;

/**
 * 
 * @author thesste
 *
 */
public class CalculateOptionPriceTest {

	/**
	 * Test method for {@link monteCarloPricing.MCPricer#calculateOptionPrice(double strike, double barrier, double maturity, String optionType, String barrierType)}.
	 */
	@Test
	public void testCalculateOptionPrice() {
		
		int numberOfPaths = 1000000;
		double maxMaturity = 1;
		double underlyingPrice = 100;
		double volatility = 0.2;
		double riskFreeRate = 0.05;
		int numberOfTradingDaysPerYear = 1;
		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		pricer.generatePaths();
		
		double strike = 90;
		double barrier = Double.NaN;
		double maturity = 1;
		String optionType = "Call";
		String barrierType = "None";
		
		double price = pricer.calculateOptionPrice(strike, barrier, maturity, optionType, barrierType);

		double benchmark = AnalyticFormulas.blackScholesOptionValue(underlyingPrice, riskFreeRate, volatility, maturity, strike);		
		double tolerance = 0.1;		
		assertEquals(benchmark, price, tolerance);		
	}

}
