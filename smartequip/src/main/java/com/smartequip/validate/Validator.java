package com.smartequip.validate;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstants;
import com.smartequip.common.CommonUtils;
import com.smartequip.common.MapperUtil;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.model.Smartequip;
import com.smartequip.service.SmartequipAnswersService;

/**
 * This class is validation purpose of question and answer.
 * 
 * @author Shraban.Rana
 *
 */
@Component
public class Validator {

	@Autowired
	private SmartequipAnswersService answersService;

	@Autowired
	private MapperUtil mapperUtil;
	
	/**
	 * This function validate the answer of asked question with various validation.
	 * Criteria: at least 3 digits in sentence. ( bcs to get sum at least 2 number
	 * required)
	 * 
	 * @param request
	 * @param token
	 * @return 
	 */
	public Smartequip validateAnswer(String request, String token) {
		List<Integer> allDigits = CommonUtils.extractAllDigits(request);

		if (allDigits.size() < 3) {
			throw new ValidationException(CommonConstants.INVALID_CLIENT_ANSWER_REQUEST);
		}
		List<Integer> qdigit = allDigits.stream().limit(allDigits.size() - 1L).collect(Collectors.toList());
		Smartequip userSmartequip = mapperUtil.mapper(qdigit, token);
		Optional<Integer> smartEquipDetails = answersService.getSmartEquipDetails(userSmartequip);
		if (!smartEquipDetails.isPresent()) {
			throw new ValidationException(CommonConstants.INVALID_CLIENT_ANSWER_REQUEST);
		} else if (!checkSyntax(request, allDigits.size() - 2)) {
			throw new ValidationException(CommonConstants.WRONG_ANSWER_FORMAT);
		} else if (!validateAnswer(allDigits, smartEquipDetails.get())) {
			throw new ValidationException(CommonConstants.WRONG_ANSWER);
		}

		return userSmartequip;
	}

	/**
	 * This function validate the format answer sentence .
	 * 
	 * @param quesNumCount
	 * @param clientAnswer
	 * @return boolean
	 */
	public boolean checkSyntax(String request, int quesNumCount) {
		String syntaxRegex = CommonConstants.USER_ANSEWER_REGEX_PART1 + String.valueOf(quesNumCount)
				+ CommonConstants.USER_ANSEWER_REGEX_PART2;
		return Pattern.compile(syntaxRegex).matcher(request).find();
	}

	/**
	 * This function validate the answer number is correct or not.
	 * 
	 * @param allDigits
	 * @param actualAnswer
	 * @return boolean
	 */
	public boolean validateAnswer(List<Integer> allDigits, int actualAnswer) {
		return allDigits.get(allDigits.size() - 1) == actualAnswer;
	}

	/**
	 * This function validate client first request is correct format or not.
	 * 
	 * @param question
	 */
	public boolean validateQuestion(String question) {
		if (!Pattern.compile(CommonConstants.USER_FIRST_QUESTION_REGEX).matcher(question).find()) {
			throw new ValidationException(CommonConstants.WRONG_QUESTION);
		}
		return true;
	}

}
