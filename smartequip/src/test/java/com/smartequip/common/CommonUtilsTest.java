package com.smartequip.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class CommonUtilsTest {

	@InjectMocks
	private CommonUtils commonUtils;

	/**
	 * Test for random no is digit or not.
	 */
	@Test
	void generateSingleRandomNoTest() {
		String generateRandomNo = String.valueOf(CommonUtils.generateSingleRandomNo());
		boolean result = Pattern.compile("^\\d+").matcher(generateRandomNo).find();
		assertEquals(true, result);

	}

	/**
	 * Test for no of generated random numbers are correct is size.
	 */
	@Test
	void generateRandomNumbersTest() {
		List<Integer> generateRandomNumbers = CommonUtils.generateRandomNumbers(3);
		assertEquals(3, generateRandomNumbers.size());
	}

	/**
	 * Test for concatenation of numbers are making string with delimiter. 
	 */
	@Test
	void getDelimiterSeparatedTest() {
		String generateRandomNumbers = CommonUtils.getDelimiterSeparated(",", Arrays.asList(1, 2, 5));
		assertEquals("1,2,5", generateRandomNumbers);
	}

	/**
	 * Test for sum of numbers are giving correct result.
	 */
	@Test
	void getSumOfNumbersTest() {
		int sumOfNumbers = CommonUtils.getSumOfNumbers(Arrays.asList(1, 2, 5));
		assertEquals(8, sumOfNumbers);
	}
}
