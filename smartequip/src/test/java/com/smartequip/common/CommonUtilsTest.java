package com.smartequip.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
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

	public static PropDetails propDetails;

	@BeforeAll
	public static void init() {
		propDetails = new PropDetails();
		propDetails.setDigitMax("2");
	}

	/**
	 * This test will validate two digit numbers.
	 */
	@Test
	void randomNumberUpToTwoDigitTest() {
		String generateRandomNo = String.valueOf(CommonUtils.randomNumberUpToTwoDigit());
		boolean result = Pattern.compile("^\\d+").matcher(generateRandomNo).find();
		assertEquals(true, result);

	}

	/**
	 * This test will validate random numbers must be 2<= number >=3. As we are
	 * setting value 'setDigitMax("2")'.
	 */
	@Test
	void maxDigitOfNumbersTest() {
		int result = CommonUtils.maxDigitOfNumbers();
		assertTrue(result >= 2 && result <= 3);
	}

	/**
	 * This test will get all digits from string sentence.
	 */
	@Test
	void extractAllDigitsTest() {
		List<Integer> expectedResult = new ArrayList<>();
		expectedResult.add(1);
		expectedResult.add(3);
		expectedResult.add(4);

		List<Integer> result = CommonUtils.extractAllDigits("find 1,3,4");
		assertTrue(result.equals(expectedResult));
	}

	/**
	 * This test will check that random numbers size >2. As this use for getting
	 * summing of numbers.
	 */
	@Test
	void collectRandomNumbersTest() {
		List<Integer> result = CommonUtils.collectRandomNumbers();
		assertTrue(result.size() >= 2);
	}

	/**
	 * Test for concatenation of numbers are making string with delimiter.
	 */
	@Test
	void getDelimiterSeparatedTest() {
		String generateRandomNumbers = CommonUtils.getDelimiterSeparated(",", Arrays.asList(1, 2, 5));
		assertEquals("1,2,5", generateRandomNumbers);
	}

}
