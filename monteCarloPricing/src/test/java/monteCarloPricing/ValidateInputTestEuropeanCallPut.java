package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateInputTestEuropeanCallPut {

	@Test
	public void testValidateInput() {
		EuropeanCallPut test = new EuropeanCallPut(100, 90, "Call");
		boolean output1 = test.validateInput("Option");
		assertEquals(false, output1);
		boolean output2 = test.validateInput("Call");
		assertEquals(true, output2);
		boolean output3 = test.validateInput("Put");
		assertEquals(true, output3);
	}

}
