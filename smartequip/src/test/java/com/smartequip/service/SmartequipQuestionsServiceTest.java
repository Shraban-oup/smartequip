package com.smartequip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartequip.cache.StoreProcess;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class SmartequipQuestionsServiceTest {

	@InjectMocks
	private SmartequipQuestionsService questionsService;

	@Mock
	private StoreProcess storeInterface;

	public static Smartequip smartequip;
	public static String generateToken;
	private static List<Integer> digitList;

	@BeforeAll
	public static void init() {
		digitList = new ArrayList<>();
		digitList.add(10);
		digitList.add(5);
		digitList.add(15);
		generateToken = "123456789103209-1232323";
		smartequip = new Smartequip(digitList, generateToken);

	}

	/**
	 * This function test whether generated numbers returning valid question format.
	 */
	@Test
	void getServerQuestionTest() {
		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			String expected = CommonConstantsUtils.SERVER_QUESTION_PREFIX + "10,5,15" + ".";
			common.when(() -> {
				CommonUtils.getDelimiterSeparated(any(), any());
			}).thenReturn("10,5,15");
			String result = questionsService.getServerQuestion(smartequip);
			assertEquals(expected, result);
		}
	}

}
