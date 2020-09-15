package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateInputTestBarrierOption {

	@Test
	public void testValidateInput() {
		KnockInOutBarrierCallPut test = new KnockInOutBarrierCallPut(null, 100, 90, "Call", "UpIn");
		boolean output1 = test.validateInput("Option", test.getValidOptionTypes());
		assertEquals(false, output1);
		boolean output2 = test.validateInput("Call", test.getValidOptionTypes());
		assertEquals(true, output2);
		boolean output3 = test.validateInput("DownOut", test.getValidBarrierTypes());
		assertEquals(true, output3);
		boolean output4 = test.validateInput("DownUp", test.getValidBarrierTypes());
		assertEquals(false, output4);
	}

}
