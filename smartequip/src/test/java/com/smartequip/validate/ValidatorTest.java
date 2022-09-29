package com.smartequip.validate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.exceptionhandler.ResourceNotFoundException;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.Smartequip;
import com.smartequip.service.SmartequipAnswersService;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

	@Mock
	private SmartequipAnswersService answersService;

	@Spy
	@InjectMocks
	private Validator validator;

	public static Smartequip smartequip;
	public static String generateToken;

	@BeforeAll
	public static void init() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		smartequip = new Smartequip(questionNums, 30);
		generateToken = new TokenGenerator().generateToken(smartequip);

	}

	@Test
	void validateAnswerTest() {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.of(smartequip));
		validator.validateAnswer(serviceAnswer, generateToken);
		verify(answersService, times(1)).getSmartEquipDetails(generateToken);

	}
	
	@Test
	void validateAnswerTest_InvalidToken() {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
				CommonConstantsUtils.INVALID_TOEKN);
	}
	
	@Test
	void validateAnswerTest_wrongFormat() {
		String serviceAnswer = "Great. The original question was “Please sum   the numbers   10,5,15” and the ans we r is 30";		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.empty());
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.of(smartequip));
		assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
				CommonConstantsUtils.WRONG_ANSWER_FORMAT);
	}
	
	@Test
	void validateAnswerTest_questionChangedByClient() {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,1,6” and the answer is 30";
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.of(smartequip));
		assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
				CommonConstantsUtils.PRE_QUESTION_CHANGES);
	}

	@Test
	void validateAnswerTest_unnessaryNumberAddedByClient() {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,1,6,14” and the answer is 30";
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.of(smartequip));
		assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
				CommonConstantsUtils.PRE_QUESTION_CHANGES);
	}

	@Test
	void validateAnswerTest_WRONG_ANSWER() {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 20";
		when(answersService.getSmartEquipDetails(generateToken)).thenReturn(Optional.of(smartequip));
		assertThrows(ValidationException.class, () -> validator.validateAnswer(serviceAnswer, generateToken),
				CommonConstantsUtils.WRONG_ANSWER);
	}

	@Test
	void validateQuestionTest() {
		String serviceQuestion = "Hey Service, can you provide me a que sds sdasds stion with to add ?";
		ValidationException assertThrows = assertThrows(ValidationException.class,
				() -> validator.validateQuestion(serviceQuestion), CommonConstantsUtils.WRONG_QUESTION);
	}

	@Test
	void validateQuestionTest_success() {
		String serviceQuestion = "Hey Service, can you provide me a question with numbers to add ?";
		validator.validateQuestion(serviceQuestion);
		verify(validator, times(1)).validateQuestion(serviceQuestion);

	}
}
