package com.smartequip.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SmartequipTest {

	@Test
	void Equaltest() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		Smartequip smartequip = new Smartequip(questionNums, 30);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(10);
		questionNums1.add(5);
		questionNums1.add(15);
		Smartequip smartequip1 = new Smartequip(questionNums1, 30);
		
		List<Integer> questionNums2 = new ArrayList<>();
		questionNums2.add(10);
		questionNums2.add(15);
		questionNums2.add(15);
		Smartequip smartequip2 = new Smartequip(questionNums2, 40);
		
		assertTrue(smartequip.equals(smartequip1));
		assertTrue(smartequip.equals(smartequip));
		assertFalse(smartequip.equals(null));
		assertFalse(smartequip.equals(new Object()));
		assertFalse(smartequip.equals(smartequip2));
	}

}
