package com.smartequip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

@ExtendWith(MockitoExtension.class)
class SmartequipQuestionsServiceTest {

	@Mock
	private StoreInterface storeInterface;

	@InjectMocks
	private SmartequipQuestionsService questionsService;

	/**
	 * This function test whether generated numbers returning valid question format.
	 */
	@Test
	void getQuestionTest() {
		try (MockedStatic<CommonUtils> utilities = mockStatic(CommonUtils.class)) {
			List<Integer> generateRandomNumbers = Arrays.asList(10, 4, 5);
			utilities.when(() -> CommonUtils.generateRandomNumbers(3)).thenReturn(generateRandomNumbers);
			String expected = CommonConstantsUtils.SERVER_QUESTION_PREFIX
					+ CommonUtils.getDelimiterSeparated(CommonConstantsUtils.COMMA, generateRandomNumbers) + ".";
			assertEquals(expected, questionsService.getQuestion("1234554545"));

		}
	}

	/**
	 * This function test correct question and answer number mapping.
	 */
	@Test
	void mapperTest() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		Smartequip smartequip = new Smartequip(questionNums, 30);
		assertEquals(smartequip.getAnsewer(), questionsService.mapper(questionNums, 30).getAnsewer());

	}

}
