package monteCarloPricing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalculateOptionPriceTest.class,
				ValidateInputTestBarrierOption.class,
				ValidateInputTestEuropeanCallPut.class })
public class AllTests {

}
