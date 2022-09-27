package com.smartequip.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommonUtilsTest {

	@InjectMocks
	private CommonUtils commonUtils;

	@Test
	void generateRandomNoTest() {
		String generateRandomNo = String.valueOf(commonUtils.generateRandomNo());
		boolean result = Pattern.compile("^\\d+").matcher(generateRandomNo).find();
		assertEquals(true, result);

	}

	@Test
	void generateRandomNumbersTest() {
		List<Integer> generateRandomNumbers = commonUtils.generateRandomNumbers(3);
		assertEquals(3, generateRandomNumbers.size());
	}

	@Test
	void getDelimiterSeparatedTest() {
		String generateRandomNumbers = commonUtils.getDelimiterSeparated(",", Arrays.asList(1, 2, 5));
		assertEquals("1,2,5", generateRandomNumbers);
	}

	@Test
	void getSumOfNumbersTest() {
		int sumOfNumbers = commonUtils.getSumOfNumbers(Arrays.asList(1, 2, 5));
		assertEquals(8, sumOfNumbers);
	}
}
