package com.smartequip.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantUtis;
import com.smartequip.common.CommonUtils;
import com.smartequip.generateToken.GenerateToken;
import com.smartequip.model.Smartequip;
//import com.smartequip.repo.SmartequipRepo;
import com.smartequip.validation.SmartequipValidation;

@Service
public class SmartequipService1 {

	Logger logger = LoggerFactory.getLogger(SmartequipService1.class);

	@Autowired
	private SmartequipValidation smartequipValidation;
		
//	@Autowired
//	private SmartequipRepo repo;
	
	@Autowired
	private Smartequip smartequip;
	
	@Autowired
	private StoreInterface storeInterface;
	
	public ResponseEntity<String> getInitResponse(String question) {
		if(smartequipValidation.validatefirstUserQUestion(question).isPresent()==false) {
			logger.info("Please ask valid question or corrent the sentence please");
			 return ResponseEntity.badRequest()
		    	      .body("Please ask valid question or corrent the sentence please");
		}
		
//		Here we are taking 3 random numbers -we can improve it Based on requirement of random sum of 2 random numbers or 3 random Number.
		int random1=CommonUtils.generateRandomNo();
		int random2=CommonUtils.generateRandomNo();
		int random3=CommonUtils.generateRandomNo();
		
		String responseQuestion=CommonConstantUtis.SERVICE_FIRST_QUESTION+random1+","+random2+","+random3;
		String finalresponse=CommonConstantUtis.SERVICE_FIRST_QUESTION_PREFIX+"\""+responseQuestion+"\".";
		
		Optional<String> generatedtokenvalue=new GenerateToken().generateToekn();
		
		mapper(random1, random2, random3, responseQuestion);
		storeInterface.addItem(generatedtokenvalue.get(), smartequip);
		logger.info("token: "+generatedtokenvalue.get()+" ,value: "+smartequip.toString());
		logger.info("finalresponse: "+finalresponse);

//		repo.save(smartequip);
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("bearer", 
	    		generatedtokenvalue.get());
	    System.out.println("finalresponse-"+finalresponse);
	    return ResponseEntity.ok()
	    	      .headers(responseHeaders)
	    	      .body(finalresponse);
	}

	private void mapper(int random1, int random2, int random3, String responseQuestion) {
		smartequip.setQuestion(responseQuestion);
		smartequip.setAnsewer(random1+random2+random3);
	}
}
