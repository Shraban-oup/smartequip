package com.smartequip.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.Smartequip;
import com.smartequip.model.SmartequipResponse;
import com.smartequip.service.SmartequipAnswersService;
import com.smartequip.service.SmartequipQuestionsService;
import com.smartequip.validate.Validator;

/**
 * @author Shraban.Rana
 * 
 *         SmartquipeController is used for client human verification.
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

	/**
	 * As per requirement, Smartequipe should have single end point '/' and client
	 * request body that's the reason of POST method selection. So new client and
	 * answer of exiting client differentiated by bearer token.
	 * 
	 * bearer in header is not mandatory for new user.
	 * 
	 * @param text
	 * @param token
	 * @return
	 */
	@PostMapping("/")
	public ResponseEntity<SmartequipResponse> smartEquipHumanClientCheck(@RequestBody String request,
			@RequestHeader(value = "bearer", defaultValue = "") String token) {

		if (!StringUtils.isEmpty(token)) {
			logger.info("OldUser: request- "+request+" , token- "+token);
			Optional<Smartequip> smartDetails = answersService.getSmartEquipDetails(token);
			Optional<String> validatedAnswer = validator.validateAnswer(request, smartDetails);
			if (validatedAnswer.isPresent()) {
				return ResponseEntity.badRequest().body(new SmartequipResponse(validatedAnswer.get(),
						HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()));
			}

			String response = answersService.getServerAnswer(token);
			return ResponseEntity.ok()
					.body(new SmartequipResponse(response, CommonConstantsUtils.SUCCESS, HttpStatus.OK.value()));

		} else {
			logger.info("newUser: request- "+request+" , token- "+token);
			Optional<String> validatedQuestion = validator.validateQuestion(request);
			if (validatedQuestion.isPresent()) {
				return ResponseEntity.badRequest().body(new SmartequipResponse(validatedQuestion.get(),
						HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()));
			}
			String newToken = tokenGenerator.generateToken();
			String response = questionsService.getQuestion(newToken);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("bearer", newToken);

			return ResponseEntity.ok().headers(responseHeaders)
					.body(new SmartequipResponse(response, CommonConstantsUtils.SUCCESS, HttpStatus.OK.value()));
		}

	}

}
