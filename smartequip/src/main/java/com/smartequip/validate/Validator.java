package com.smartequip.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

@Component
public class Validator {

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

	public Optional<String> validateQuestion(String question) {
		if (!Pattern.compile(CommonConstantsUtils.USER_FIRST_QUESTION_REGEX).matcher(question).find()) {
			return Optional.of(CommonConstantsUtils.WRONG_QUESTION);
		}
		return Optional.empty();
	}

	public boolean validateAnswer(List<Integer> allDigits, int actualAnswer) {
		if (allDigits.get(3) == actualAnswer) {
			return true;
		}
		return false;
	}

	public boolean checkAnswerFormat(String clientAnswer) {
		return Pattern.compile(CommonConstantsUtils.USER_ANSEWER_REGEX).matcher(clientAnswer).find();
	}

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
