package com.smartequip.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.exceptionhandler.ResourceNotFoundException;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.model.Smartequip;
import com.smartequip.service.SmartequipAnswersService;

/**
 * This class is validation purpose of question and answer.
 * @author Shraban.Rana
 *
 */
@Component
public class Validator {

	@Autowired
	private SmartequipAnswersService answersService;
	
	
	/**
	 * This function validate the answer of asked question with various validation.
	 * @param request
	 * @param token
	 */
	public void validateAnswer(String request, String token) {
		Optional<Smartequip> smartDetails = answersService.getSmartEquipDetails(token);
		if (!smartDetails.isPresent()) {
			throw new ResourceNotFoundException(CommonConstantsUtils.INVALID_TOEKN);
		} else if (!checkAnswerFormat(request)) {
			throw new ValidationException(CommonConstantsUtils.WRONG_ANSWER_FORMAT);
		}
		List<Integer> allDigits = CommonUtils.extractAllDigits(request);

		if (!validatePreQuestionNums(allDigits, smartDetails.get().getQuestionNums())) {
			throw new ValidationException(CommonConstantsUtils.PRE_QUESTION_CHANGES);
		} else if (!validateAnswer(allDigits, smartDetails.get().getAnsewer())) {
			throw new ValidationException(CommonConstantsUtils.WRONG_ANSWER);
		}
	}

	
	/**
	 * This function validate client first request is correct format or not.
	 * @param question
	 */
	public void validateQuestion(String question) {
		if (!Pattern.compile(CommonConstantsUtils.USER_FIRST_QUESTION_REGEX).matcher(question).find()) {
			throw new ValidationException(CommonConstantsUtils.WRONG_QUESTION);
		}
	}

	/**
	 * This function validate the answer number is correct or not.
	 * @param allDigits
	 * @param actualAnswer
	 * @return boolean
	 */
	public boolean validateAnswer(List<Integer> allDigits, int actualAnswer) {
		if (allDigits.get(3) == actualAnswer) {
			return true;
		}
		return false;
	}

	/**
	 * This function validate the format answer sentence .
	 * @param clientAnswer
	 * @return boolean
	 */
	public boolean checkAnswerFormat(String clientAnswer) {
		return Pattern.compile(CommonConstantsUtils.USER_ANSEWER_REGEX).matcher(clientAnswer).find();
	}

	/**
	 * This function validate the asked question numbers are same or changes.
	 * @param allDigits
	 * @param actialnumbers
	 * @return
	 */
	public boolean validatePreQuestionNums(List<Integer> allDigits, List<Integer> actialnumbers) {
		if (allDigits.size() > 4) {
			return false;
		}
		List<Integer> questionsdigit = Arrays.asList(allDigits.get(0), allDigits.get(1), allDigits.get(2));
		if (questionsdigit.equals(actialnumbers)) {
			return true;
		}
		return false;
	}

}
