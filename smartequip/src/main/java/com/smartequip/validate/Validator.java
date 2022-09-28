package com.smartequip.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

/**
 * This class is validation purpose of question and answer.
 * @author Shraban.Rana
 *
 */
@Component
public class Validator {

	/**
	 * This function validate the answer of asked question with various validation.
	 * @param request
	 * @param smartDetails
	 * @return Optional<String>
	 */
	public Optional<String> validateAnswer(String request, Optional<Smartequip> smartDetails) {
		List<Integer> allDigits = CommonUtils.extractAllDigits(request);

		if (!smartDetails.isPresent()) {
			return Optional.of(CommonConstantsUtils.INVALID_TOEKN);
		} else if (!checkAnswerFormat(request)) {
			return Optional.of(CommonConstantsUtils.WRONG_ANSWER_FORMAT);
		} else if (!validatePreQuestionNums(allDigits, smartDetails.get().getQuestionNums())) {
			return Optional.of(CommonConstantsUtils.PRE_QUESTION_CHANGES);
		} else if (!validateAnswer(allDigits, smartDetails.get().getAnsewer())) {
			return Optional.of(CommonConstantsUtils.WRONG_ANSWER);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * This function validate client first request is correct format or not.
	 * @param question
	 * @return Optional<String>
	 */
	public Optional<String> validateQuestion(String question) {
		if (!Pattern.compile(CommonConstantsUtils.USER_FIRST_QUESTION_REGEX).matcher(question).find()) {
			return Optional.of(CommonConstantsUtils.WRONG_QUESTION);
		}
		return Optional.empty();
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
