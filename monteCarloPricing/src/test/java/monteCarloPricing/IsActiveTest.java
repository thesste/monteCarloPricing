package monteCarloPricing;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author thesste
 *
 */
public class IsActiveTest {

	/**
	 * Test method for {@link monteCarloPricing.KnockInOutBarrierCallPut#isActive(double[])}.
	 */
	@Test
	public void testIsActive() {	
		double[] underlyingPath1 = {100, 97, 130};
		double[] underlyingPath2 = {100, 112.5, 92};

		KnockInOutBarrierCallPut barrier1 = new KnockInOutBarrierCallPut(100, 120, "Put", "UpIn");
		assertTrue(barrier1.isActive(underlyingPath1));
		assertFalse(barrier1.isActive(underlyingPath2));

		KnockInOutBarrierCallPut barrier2 = new KnockInOutBarrierCallPut(100, 120, "Put", "UpOut");
		assertFalse(barrier2.isActive(underlyingPath1));
		assertTrue(barrier2.isActive(underlyingPath2));
	}

}
