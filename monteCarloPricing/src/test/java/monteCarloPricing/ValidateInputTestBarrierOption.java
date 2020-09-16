package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author thesste
 *
 */
public class ValidateInputTestBarrierOption {

	/**
	 * Test method for {@link monteCarloPricing.KnockInOutBarrierCallPut#validateInput(String input, String[] validInputs)}.
	 */
	@Test
	public void testValidateInput() {
		KnockInOutBarrierCallPut test = new KnockInOutBarrierCallPut(100, 90, "Call", "UpIn");
		
		boolean output1 = test.validateInput("Option", test.getValidOptionTypes());
		assertFalse(output1);
		
		boolean output2 = test.validateInput("Call", test.getValidOptionTypes());
		assertTrue(output2);
		
		boolean output3 = test.validateInput("DownOut", test.getValidBarrierTypes());
		assertTrue(output3);
		
		boolean output4 = test.validateInput("DownUp", test.getValidBarrierTypes());
		assertFalse(output4);
	}

}
