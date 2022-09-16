package com.smartequip.controller;

import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private SmartequipService1 service1;

	@Autowired
	private SmartequipService2 service2;

	/*
	 * as per requirement app should have single endpoint "/". we can improve it by
	 * doing spring cloud api getway for endpoint redirection and validation of user
	 */
	@PostMapping("/api")
	public ResponseEntity<String> createTutorial(@RequestBody String text,
			@RequestHeader(value = "bearer", defaultValue = "") String token) {

		System.out.println("token value :"+token);
		if (StringUtils.isEmpty(token)) {
			return service1.getInitResponse(text);
		}

		return service2.validateResponse(text, token);
	}

}
