package com.smartequip.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartequip.common.CommonUtils;

@Service
public class SmartequipService2 {

	
	public ResponseEntity<String> validateResponse(String text, String token) {


		//get question and answer from DB
		
		String questionString="Please sum the numbers 9,5,3";
		String answerString="the answer is";
		Integer answer=17;

		//validation , later we will make a new function for this
		if(text.contains(questionString) && text.contains(answerString)) {
			System.out.println("index of : "+text.indexOf(answerString));
			System.out.println("substring "+text.substring(text.indexOf(answerString)));
			System.out.println("no "+CommonUtils.extractNumbers(text.substring(text.indexOf(answerString))).isPresent());
			Optional<Integer> extractNumbers = CommonUtils.extractNumbers(text.substring(text.indexOf(answerString)));
			if(extractNumbers.isPresent() && extractNumbers.get().intValue()==answer) {
				return ResponseEntity.ok().body("That’s great");
				//remove that record in DB as it already verify human. 

			}
		}
		
		return ResponseEntity.badRequest().body("That’s wrong. Please try again.");
	}

}
