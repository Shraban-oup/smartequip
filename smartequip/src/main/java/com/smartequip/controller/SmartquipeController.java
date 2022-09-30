package com.smartequip.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.CommonUtils;
import com.smartequip.common.MapperUtil;
import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.Smartequip;
import com.smartequip.model.SmartequipResponse;
import com.smartequip.service.SmartequipAnswersService;
import com.smartequip.service.SmartequipQuestionsService;
import com.smartequip.validate.Validator;

/**
 * SmartquipeController is used for client human verification.
 * 
 * @author Shraban.Rana
 * 
 */
@RestController
public class SmartquipeController {

	Logger logger = LoggerFactory.getLogger(SmartquipeController.class);

	@Autowired
	private SmartequipQuestionsService questionsService;

	@Autowired
	private SmartequipAnswersService answersService;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private Validator validator;
	
	@Autowired
	private MapperUtil mapperUtil;

	/**
	 * As per requirement, Smartequipe should have single end point '/' and client
	 * request body that's the reason of POST method selection. So new client and
	 * answer of exiting client differentiated by bearer token.
	 * 
	 * bearer in header is not mandatory for new user.
	 * 
	 * consumes = { MediaType.ALL_VALUE }: because This is human verification APP
	 * and also we are validating format of request sentence/text.
	 * 
	 * produces = { MediaType.APPLICATION_JSON_VALUE }: as we are sending code and
	 * body etc.. so JSON format is best choice.
	 * 
	 * @param text
	 * @param token
	 * @return
	 */
	@PostMapping(name = "/", consumes = { MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<SmartequipResponse> smartEquipHumanClientCheck(@RequestBody String request,
			@RequestHeader(value = "bearer", defaultValue = "") String token) {

		if (!StringUtils.isEmpty(token)) {
			Smartequip smartequip = validator.validateAnswer(request, token);
			String response = answersService.getServerAnswer(smartequip);
			return ResponseEntity.ok()
					.body(new SmartequipResponse(response, CommonConstantsUtils.SUCCESS, HttpStatus.OK.value()));

		} else {
			validator.validateQuestion(request);
			List<Integer> ranNumbers = CommonUtils.collectRandomNumbers();
			String uniqueToken = tokenGenerator.createUniqueToken(ranNumbers);
			Smartequip smartequip = mapperUtil.mapper(ranNumbers, uniqueToken);
			String response = questionsService.getServerQuestion(smartequip);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("bearer", uniqueToken);

			return ResponseEntity.ok().headers(responseHeaders)
					.body(new SmartequipResponse(response, CommonConstantsUtils.SUCCESS, HttpStatus.OK.value()));
		}

	}

}
