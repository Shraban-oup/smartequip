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
	 * @param smartequip 
	 * @param randomNumbers 
	 * @param newtoken
	 * @return
	 */
	public String getServerQuestion(String uniqueToken, Smartequip smartequip, List<Integer> randomNumbers) {
		storeInterface.addItem(uniqueToken, smartequip);
		logger.info("token: " + uniqueToken + " ,value: " + smartequip.toString());

		return CommonConstantsUtils.SERVER_QUESTION_PREFIX
				+ CommonUtils.getDelimiterSeparated(CommonConstantsUtils.COMMA, randomNumbers) + ".";
	}
	

}
