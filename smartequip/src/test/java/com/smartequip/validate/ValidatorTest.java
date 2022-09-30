package com.smartequip.validate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.common.MapperUtil;
import com.smartequip.common.PropDetails;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.model.Smartequip;
import com.smartequip.service.SmartequipAnswersService;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class ValidatorTest {

	@InjectMocks
	private Validator validator;

	@Mock
	private SmartequipAnswersService answersService;

	@Mock
	private MapperUtil mapperUtil;

	private static Smartequip smartequip;
	private static String generateToken;
	private static PropDetails propDetails;
	private static List<Integer> digitList;
	private static int answer;

	@BeforeAll
	public static void init() {
		digitList = new ArrayList<>();
		digitList.add(10);
		digitList.add(5);
		digitList.add(15);
		generateToken = "123456789103209-1232323";
		smartequip = new Smartequip(digitList, generateToken);
		answer = 30;
		propDetails = new PropDetails();
		propDetails.setDigitMax("3");
	}

	/**
	 * Test for answer request sentence minimum have 3 numbers. 1st two sum is 3rd.
	 */
	@Test
	void validateAnswerTest_sizeLessthen3() {

		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			List<Integer> digitList2value = new ArrayList<>();
			digitList2value.add(10);
			digitList2value.add(5);
			String serviceAnswer = "Great. The original question was “Please sum the numbers 10” and the answer is 30";
			common.when(() -> {
				CommonUtils.extractAllDigits(any());
			}).thenReturn(digitList2value);
			assertThrows(ValidationException.class, () -> {
				validator.validateAnswer(serviceAnswer, generateToken);
			}, CommonConstantsUtils.INVALID_CLIENT_ANSWER_REQUEST);
		}
	}

	/**
	 * Test for request have invalid token
	 */
	@Test
	void validateAnswerTest_invalidToken() {
		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
			String invalidtoken = "28312il321kh3j2hb3";
			Smartequip newSmartequip = new Smartequip(digitList, invalidtoken);

			common.when(() -> {
				CommonUtils.extractAllDigits(any());
			}).thenReturn(digitList);

			when(mapperUtil.mapper(any(), any())).thenReturn(newSmartequip);
			when(answersService.getSmartEquipDetails(any())).thenReturn(Optional.empty());

			assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, invalidtoken),
					CommonConstantsUtils.INVALID_CLIENT_ANSWER_REQUEST);
		}
	}

	/**
	 * Test for request is wrong syntax
	 */
	@Test
	void validateAnswerTest_syntaxWrong() {
		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			String serviceAnswer = "Great. The original question was “Please sum the    numbers   10,5,15” and the answer is 30";
			common.when(() -> {
				CommonUtils.extractAllDigits(any());
			}).thenReturn(digitList);

			when(mapperUtil.mapper(any(), any())).thenReturn(smartequip);
			when(answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(answer));
			assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
					CommonConstantsUtils.WRONG_ANSWER_FORMAT);
		}
	}

	/**
	 * Test for wrong answer.
	 */
	@Test
	void validateAnswerTest_wrongAnswer() {
		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			List<Integer> totalDigit = new ArrayList<>();
			totalDigit.addAll(digitList);
			totalDigit.add(20);
			String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 20";
			common.when(() -> {
				CommonUtils.extractAllDigits(any());
			}).thenReturn(totalDigit);

			when(mapperUtil.mapper(any(), any())).thenReturn(smartequip);
			when(answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(answer));
			assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
					CommonConstantsUtils.WRONG_ANSWER);
		}
	}

	/**
	 * Test need to pass for correct answer
	 */
	@Test
	void validateAnswerTest_correctAnswer() {
		try (MockedStatic<CommonUtils> common = mockStatic(CommonUtils.class)) {
			List<Integer> totalDigit = new ArrayList<>();
			totalDigit.addAll(digitList);
			totalDigit.add(30);
			String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
			common.when(() -> {
				CommonUtils.extractAllDigits(any());
			}).thenReturn(totalDigit);

			when(mapperUtil.mapper(any(), any())).thenReturn(smartequip);
			when(answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(answer));
			Smartequip result = validator.validateAnswer(serviceAnswer, generateToken);
			assertEquals(smartequip.getUniqueToken(), result.getUniqueToken());
		}
	}

	/**
	 * Wrong syntax question asked by new client
	 */
	@Test
	void validateQuestionTest_wrongQuestion() {
		String serviceQuestion = "Hey Service, can you provide me a question with numbers     to add ?";

		assertThrows(ValidationException.class, () -> validator.validateQuestion(serviceQuestion),
				CommonConstantsUtils.WRONG_QUESTION);
	}

	/**
	 * Valid request question asked by new client
	 */
	@Test
	void validateQuestionTest_validQuestion() {
		String serviceQuestion = "Hey Service, can you provide me a question with numbers to add ?";
		boolean result = validator.validateQuestion(serviceQuestion);
		assertEquals(true, result);
	}
}
