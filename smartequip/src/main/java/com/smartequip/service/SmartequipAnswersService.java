package com.smartequip.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.model.Smartequip;

/**
 * This service class do operation on final answer of client human verification
 * @author Shraban.Rana
 *
 */
@Service
public class SmartequipAnswersService {

	Logger logger = LoggerFactory.getLogger(SmartequipAnswersService.class);

	@Autowired
	private StoreInterface storeInterface;

	/**
	 * This function delete the exiting details from cache and return final response.
	 * This function will only call when user is verified as human with correct answer.
	 * @param token
	 * @return
	 */
	public String getServerAnswer(String token) {
		storeInterface.deleteItem(token);
		return CommonConstantsUtils.CORRECT_ANSWER;
	}

	/**
	 * This function get details of questions numbers and actual answer of asked question.
	 * @param token
	 * @return
	 */
	public Optional<Smartequip> getSmartEquipDetails(String token) {
		return storeInterface.getItem(token);
	}

}
