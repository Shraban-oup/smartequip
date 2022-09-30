package com.smartequip.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	void toStringtest() {

		Smartequip smartequip = new Smartequip();
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		smartequip.setQuestionNums(questionNums);
		smartequip.setUniqueToken("adsadw323232");
		assertNotNull(smartequip.toString());
	}

	@Test
	void propertiesTest() {

		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		Smartequip smartequip = new Smartequip(questionNums, "weqwewewq23232");
		assertEquals("weqwewewq23232", smartequip.getUniqueToken());
		assertEquals(questionNums, smartequip.getQuestionNums());
	}

	@Test
	void hashCodeTest() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		int hash = Objects.hash(questionNums, uniqueToken);
		assertEquals(hash, smartequip.hashCode());
	}
	
	@Test
	void equalMethod_sameObject() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		assertEquals(true, smartequip.equals(smartequip));
	}
	
	@Test
	void equalMethod_nullObject() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		assertEquals(false, smartequip.equals(null));
	}
	
	@Test
	void equalMethod_differentObject() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		assertEquals(false, smartequip.equals(new Object()));
	}
	
	@Test
	void equalMethod_differetNumbers() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(1);
		questionNums1.add(2);
		questionNums1.add(5);
		String uniqueToken1="weqwewewq23232";
		Object smartequipnew = (Object)new Smartequip(questionNums1, uniqueToken1);
		
		assertEquals(false, smartequip.equals(smartequipnew));
	}
	
	@Test
	void equalMethod_differetToken() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(1);
		questionNums1.add(2);
		questionNums1.add(3);
		String uniqueToken1="weqwewewq555555";
		Object smartequipnew = (Object)new Smartequip(questionNums1, uniqueToken1);
		
		assertEquals(false, smartequip.equals(smartequipnew));
	}
	
	@Test
	void equalMethod_differetTokenAndquestion() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(1);
		questionNums1.add(2);
		questionNums1.add(13);
		String uniqueToken1="weqwewewq555555";
		Object smartequipnew = (Object)new Smartequip(questionNums1, uniqueToken1);
		
		assertEquals(false, smartequip.equals(smartequipnew));
	}
	

	@Test
	void equalMethod_sameTokenAndquestion() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(1);
		questionNums.add(2);
		questionNums.add(3);
		String uniqueToken="weqwewewq23232";
		Smartequip smartequip = new Smartequip(questionNums, uniqueToken);
		
		List<Integer> questionNums1 = new ArrayList<>();
		questionNums1.add(1);
		questionNums1.add(2);
		questionNums1.add(3);
		Object smartequipnew = (Object)new Smartequip(questionNums1, uniqueToken);
		
		assertEquals(true, smartequip.equals(smartequipnew));
	}
}
