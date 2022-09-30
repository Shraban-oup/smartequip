package com.smartequip.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreProcess;
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
	private StoreProcess storeInterface;

	/**
	 * This function delete the exiting details from cache and return final response.
	 * This function will only call when user is verified as human with correct answer.
	 * @param smartequip
	 * @return
	 */
	public String getServerAnswer(Smartequip smartequip) {
		storeInterface.deleteItem(smartequip);
		return CommonConstantsUtils.CORRECT_ANSWER;
	}

	/**
	 * This function get details of with client smartequip details.
	 * @param token
	 * @return
	 */
	public Optional<Integer> getSmartEquipDetails(Smartequip smartequip) {
		return storeInterface.getItem(smartequip);
	}

}
