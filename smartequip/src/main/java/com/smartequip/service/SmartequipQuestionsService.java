package com.smartequip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreProcess;
import com.smartequip.common.CommonConstants;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

/**
 * This service class do operation on first client request for human
 * verification
 * 
 * @author Shraban.Rana
 *
 */
@Service
public class SmartequipQuestionsService {

	Logger logger = LoggerFactory.getLogger(SmartequipQuestionsService.class);

	@Autowired
	private StoreProcess storeInterface;

	/**
	 * This function will register client unique record by calling HASHCODE and
	 * equal method. And it will sent response with number of random digits. Max
	 * Number of digits asked in question has mentioned in properties file.
	 * 
	 * @param smartequip
	 * @param newtoken
	 * @return
	 */
	public String getServerQuestion(Smartequip smartequip) {
		int sumOfNumbers = smartequip.getQuestionNums().stream().mapToInt(Integer::intValue).sum();
		storeInterface.addItem(smartequip, sumOfNumbers);
		return CommonConstants.SERVER_QUESTION_PREFIX
				+ CommonUtils.getDelimiterSeparated(CommonConstants.COMMA, smartequip.getQuestionNums()) + ".";
	}

}
