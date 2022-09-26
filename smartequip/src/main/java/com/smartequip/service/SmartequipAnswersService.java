package com.smartequip.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.model.Smartequip;

@Service
public class SmartequipAnswersService {

	Logger logger = LoggerFactory.getLogger(SmartequipAnswersService.class);

	@Autowired
	private StoreInterface storeInterface;

	public String getServerAnswer(String token) {
		storeInterface.deleteItem(token);
		return CommonConstantsUtils.CORRECT_ANSWER;
	}

	public Optional<Smartequip> getSmartEquipDetails(String token) {
		return storeInterface.getItem(token);
	}

}
