package com.smartequip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

/**
 * @author Shraban.Rana
 *
 */
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
	void getServerQuestionTest() {
	//	try (MockedStatic<CommonUtils> utilities = mockStatic(CommonUtils.class)) {
		
			List<Integer> questionNums = new ArrayList<>();
			questionNums.add(10);
			questionNums.add(5);
			questionNums.add(15);
			Smartequip smartequip = new Smartequip(questionNums, 30);
			List<Integer> generateRandomNumbers = Arrays.asList(10, 4, 5);
			String expected = CommonConstantsUtils.SERVER_QUESTION_PREFIX
					+ CommonUtils.getDelimiterSeparated(CommonConstantsUtils.COMMA, generateRandomNumbers) + ".";
			doNothing().when(storeInterface).addItem(any(), any());
			assertEquals(expected, questionsService.getServerQuestion("123456", smartequip, generateRandomNumbers));

	//	}
	}

}
