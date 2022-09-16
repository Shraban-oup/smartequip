package com.smartequip.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstantUtis;

@Component
public class SmartequipValidation {
	
	public Optional<String> validatefirstUserQUestion(String question){
		if(StringUtils.contains(question, CommonConstantUtis.USER_FIRST_QUESTION)) {
			System.out.println("question passed ,"+question);
			return Optional.of(question);
		}
		System.out.println("question failed ,"+question);
		return Optional.empty();
	}
}
