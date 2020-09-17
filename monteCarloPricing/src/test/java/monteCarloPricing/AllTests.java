package monteCarloPricing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalculateOptionPriceTest.class,
				ValidateInputTestBarrierOption.class,
				ValidateInputTestEuropeanCallPut.class,
				EuropeanCallPutPayoffTest.class,
				BarrierCallPutPayoffTest.class,
				IsActiveTest.class,
				CalculateDiscountedPayoffTest.class,
				CalculateGreeksTest.class})
/**
 * Class to run all tests at once
 * @author thesste
 *
 */
public class AllTests {

}
