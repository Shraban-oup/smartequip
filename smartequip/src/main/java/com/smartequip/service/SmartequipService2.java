package com.smartequip.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantUtis;
import com.smartequip.common.CommonUtils;
import com.smartequip.model.Smartequip;

@Service
public class SmartequipService2 {

	Logger logger = LoggerFactory.getLogger(SmartequipService2.class);

	@Autowired
	private StoreInterface storeInterface;
	
	public ResponseEntity<String> checkAnswer(String text, String token) {

		Optional<Smartequip> item = storeInterface.getItem(token);
		if(!item.isPresent()) {
			return ResponseEntity.badRequest().body(CommonConstantUtis.INVALID_TOEKN);
		}
		logger.info("token is exits with value: "+item.get().toString());
		
		if(!text.contains(item.get().getQuestion()) || !text.contains(CommonConstantUtis.SERVICE_ANSEWER_PREFIX)) {
			return ResponseEntity.badRequest().body(CommonConstantUtis.WRONG_ANSWER +"            "+CommonConstantUtis.EXPECTED_ANSWER_FORMAT);
		}

		if(validateAnswer(text,item.get())) {
			storeInterface.deleteItem(token);
			return ResponseEntity.ok().body(CommonConstantUtis.CORRECT_ANSWER);
		}
		return ResponseEntity.badRequest().body(CommonConstantUtis.WRONG_ANSWER);
	}
	

	public boolean validateAnswer(String text,Smartequip exitDetails) {
			Optional<Integer> useranswer = CommonUtils.extractNumbers(text.substring(text.indexOf(CommonConstantUtis.SERVICE_ANSEWER_PREFIX)));
			return useranswer.isPresent() && useranswer.get().intValue()==exitDetails.getAnsewer();
	}
	
	
}
