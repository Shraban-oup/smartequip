package com.smartequip.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Shraban.Rana
 *
 */
class SmartequipResponseTest {

	/**
	 * Only for coverage purpose.
	 */
	@Test
	void test() {
		SmartequipResponse response = new SmartequipResponse();
		response.setMessage("response message");
		response.setStatus("success");
		response.setStatusCode(200);
		assertEquals(200, response.getStatusCode());
	}

}
