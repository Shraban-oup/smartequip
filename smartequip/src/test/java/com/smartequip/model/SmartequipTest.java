package com.smartequip.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class SmartequipTest {

	/**
	 * This will cover all equal method scenarios
	 */
	@Test
	void Equaltest() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		String token="asdskajdbehjbr";
		Smartequip smartequip = new Smartequip(questionNums, token);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(10);
		questionNums1.add(5);
		questionNums1.add(15);
		Smartequip smartequip1 = new Smartequip(questionNums1, token);
		
		List<Integer> questionNums2 = new ArrayList<>();
		questionNums2.add(10);
		questionNums2.add(15);
		questionNums2.add(15);
		String token2="asdskajdbehjbrere2322";
		Smartequip smartequip2 = new Smartequip(questionNums2, token2);
		
		assertTrue(smartequip.equals(smartequip1));
		assertTrue(smartequip.equals(smartequip));
		assertFalse(smartequip.equals(null));
		assertFalse(smartequip.equals(new Object()));
		assertFalse(smartequip.equals(smartequip2));
		
		List<Integer> questionNums3 = new ArrayList<>();
		questionNums3.add(10);
		questionNums3.add(15);
		String token3="asdskajdbeh343jbrere2322";
		Smartequip smartequip3 = new Smartequip(questionNums3, token3);
		String result = smartequip3.toString();
		assertNotNull(result);
		
	}

}
