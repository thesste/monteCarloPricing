package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateInputTestEuropeanCallPut {

	@Test
	public void testValidateInput() {
		EuropeanCallPut test = new EuropeanCallPut(110, 100, "Call");
		
		boolean output1 = test.validateInput("Option");
		assertFalse(output1);
		
		boolean output2 = test.validateInput("Call");
		assertTrue(output2);
		
		boolean output3 = test.validateInput("Put");
		assertTrue(output3);
	}

}
