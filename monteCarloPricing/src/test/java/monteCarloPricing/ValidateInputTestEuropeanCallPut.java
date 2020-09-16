package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author thesste
 *
 */
public class ValidateInputTestEuropeanCallPut {

	/**
	 * Test method for {@link monteCarloPricing.EuropeanCallPut#validateInput(String input)}.
	 */
	@Test
	public void testValidateInput() {
		EuropeanCallPut test = new EuropeanCallPut(100, "Call");
		
		boolean output1 = test.validateInput("Option");
		assertFalse(output1);
		
		boolean output2 = test.validateInput("Call");
		assertTrue(output2);
		
		boolean output3 = test.validateInput("Put");
		assertTrue(output3);
	}

}
