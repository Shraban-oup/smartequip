package com.smartequip.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

/**
 * This service class do operation on first client request for human verification
 * @author Shraban.Rana
 *
 */
@Service
public class SmartequipQuestionsService {

	Logger logger = LoggerFactory.getLogger(SmartequipQuestionsService.class);

	@Autowired
	private StoreInterface storeInterface;

	/**
	 * This will sent response with question and token for subsequent requests.
	 * @param newtoken
	 * @return
	 */
	public String getQuestion(String newtoken) {
		List<Integer> generateRandomNumbers = CommonUtils.generateRandomNumbers(3);
		Smartequip smartequip = mapper(generateRandomNumbers, CommonUtils.getSumOfNumbers(generateRandomNumbers));
		storeInterface.addItem(newtoken, smartequip);
		logger.info("token: " + newtoken + " ,value: " + smartequip.toString());

		return CommonConstantsUtils.SERVER_QUESTION_PREFIX
				+ CommonUtils.getDelimiterSeparated(CommonConstantsUtils.COMMA, generateRandomNumbers) + ".";
	}

	/**
	 * This function mapped to stored asked questions numbers and final answer.
	 * @param generateRandomNumbers
	 * @param answer
	 * @return
	 */
	public Smartequip mapper(List<Integer> generateRandomNumbers, int answer) {
		Smartequip smartequip=new Smartequip();
		smartequip.setQuestionNums(generateRandomNumbers);
		smartequip.setAnsewer(answer);
		return smartequip;
	}

}
