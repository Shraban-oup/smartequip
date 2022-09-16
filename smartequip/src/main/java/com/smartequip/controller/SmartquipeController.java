package com.smartequip.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartequip.service.SmartequipService1;
import com.smartequip.service.SmartequipService2;

@RestController
public class SmartquipeController {

	Logger logger = LoggerFactory.getLogger(SmartquipeController.class);

	@Autowired
	private SmartequipService1 service1;

	@Autowired
	private SmartequipService2 service2;

	/*
	 * as per requirement app should have single endpoint "/". we can improve it by
	 * doing spring cloud api getway for endpoint redirection and validation of user
	 */
	@PostMapping("/")
	public ResponseEntity<String> createTutorial(@RequestBody String text,
			@RequestHeader(value = "bearer", defaultValue = "") String token) {

		if (StringUtils.isEmpty(token)) {
			logger.info("new user");
			return service1.getInitResponse(text);
		}

		logger.info("existing user with token: "+token +" ,text: "+text);
		return service2.checkAnswer(text, token);
	}

}
