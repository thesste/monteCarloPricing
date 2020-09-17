/**
 * 
 */
package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

import net.finmath.functions.AnalyticFormulas;

/**
 * @author thesste
 *
 */
public class CalculateGreeksTest {

	/**
	 * Test method for {@link monteCarloPricing.MCPricer#calculateDelta(double, double, double, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCalculateDelta() {

		int numberOfPaths = 1000000;
		double maxMaturity = 1;
		double underlyingPrice = 100;
		double volatility = 0.3;
		double riskFreeRate = 0.2;
		int numberOfTradingDaysPerYear = 1;
		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		pricer.generatePaths();
		
		double strike = 90;
		double maturity = 1;
		String optionType = "Call";
		String barrierType = "None";
		double barrier = Double.NaN;
		
		double delta = pricer.calculateDelta(strike, barrier, maturity, optionType, barrierType);		

		double benchmark = AnalyticFormulas.blackScholesOptionDelta(underlyingPrice, riskFreeRate, volatility, maturity, strike);		
		assertEquals(benchmark, delta, 0.01);	
	}

	/**
	 * Test method for {@link monteCarloPricing.MCPricer#calculateGamma(double, double, double, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCalculateGamma() {
		
		int numberOfPaths = 1000000;
		double maxMaturity = 1;
		double underlyingPrice = 100;
		double volatility = 0.3;
		double riskFreeRate = 0.2;
		int numberOfTradingDaysPerYear = 1;
		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		pricer.generatePaths();
		
		double strike = 85;
		double maturity = 1;
		String optionType = "Call";
		
		String barrierType = "None";
		double barrier = Double.NaN;
		
		double delta = pricer.calculateGamma(strike, barrier, maturity, optionType, barrierType);		

		double benchmark = AnalyticFormulas.blackScholesOptionGamma(underlyingPrice, riskFreeRate, volatility, maturity, strike);		
		assertEquals(benchmark, delta, 0.001);
		}

	/**
	 * Test method for {@link monteCarloPricing.MCPricer#calculateVega(double, double, double, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCalculateVega() {
		
		int numberOfPaths = 1000000;
		double maxMaturity = 1;
		double underlyingPrice = 100;
		double volatility = 0.3;
		double riskFreeRate = 0.2;
		int numberOfTradingDaysPerYear = 1;
		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		pricer.generatePaths();
		
		double strike = 95;
		double maturity = 1;
		String optionType = "Call";
		
		String barrierType = "None";
		double barrier = Double.NaN;
		
		double delta = pricer.calculateVega(strike, barrier, maturity, optionType, barrierType);		

		double benchmark = AnalyticFormulas.blackScholesOptionVega(underlyingPrice, riskFreeRate, volatility, maturity, strike);		
		assertEquals(benchmark / (double) 100, delta, 0.01);
		}

}
