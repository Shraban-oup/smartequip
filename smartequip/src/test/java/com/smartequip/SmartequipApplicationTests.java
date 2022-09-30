package com.smartequip;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for main SmartequipApplication
 * @author Shraban.Rana
 *
 */
class SmartequipApplicationTest {

	/**
	 * This will cover the code coverage of application main class
	 */
	@Test
	void contextLoads() {
		SmartequipApplication.main(new String[] {});
		assertTrue(true);
	}

}
