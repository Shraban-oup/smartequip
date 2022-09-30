package com.smartequip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartequip.common.CommonConstants;
import com.smartequip.common.CommonUtils;
import com.smartequip.common.MapperUtil;
import com.smartequip.common.PropDetails;
import com.smartequip.exceptionhandler.TokenException;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.model.ErrorMessage;
import com.smartequip.model.Smartequip;
import com.smartequip.service.SmartequipAnswersService;
import com.smartequip.service.SmartequipQuestionsService;
import com.smartequip.tokengenerator.TokenGenerator;
import com.smartequip.validate.Validator;

/**
 * @author Shraban.Rana
 *
 */
@WebMvcTest(controllers = SmartquipeController.class)
class SmartquipeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SmartequipQuestionsService questionsService;

	@MockBean
	private SmartequipAnswersService answersService;

	@MockBean
	private TokenGenerator tokenGenerator;

	@MockBean
	private Validator validator;

	@MockBean
	private MapperUtil mapperUtil;

	public static Smartequip smartequip;
	public static String generateToken;
	public static PropDetails propDetails;
	private static List<Integer> digitList;

	@BeforeAll
	public static void init() {
		digitList = new ArrayList<>();
		digitList.add(10);
		digitList.add(5);
		digitList.add(15);
		generateToken = "123456789103209-1232323";
		smartequip = new Smartequip(digitList, generateToken);

		propDetails = new PropDetails();
		propDetails.setDigitMax("3");
	}

	/**
	 * Test for new client requested question correctly.
	 * 
	 * @throws Exception
	 */
	@Test
	void smartEquipHumanClientCheck_validQuestion() throws Exception {
		String serviceQuestion = "Here you go, solve the question: Please sum the numbers 10,4,6.";
		String clientRequest = "Hey Service, can you provide me a question with numbers to add ?";
		try (MockedStatic<CommonUtils> commonUtils = mockStatic(CommonUtils.class)) {
			when(validator.validateQuestion(any())).thenReturn(true);
			commonUtils.when(() -> {
				CommonUtils.collectRandomNumbers();
			}).thenReturn(digitList);
			when(mapperUtil.mapper(any(), any())).thenReturn(smartequip);
			when(this.tokenGenerator.createUniqueToken(any())).thenReturn(generateToken);
			when(questionsService.getServerQuestion(any())).thenReturn(serviceQuestion);

			MvcResult requestResult = this.mockMvc.perform(post("/").content(clientRequest)).andExpect(status().isOk())
					.andReturn();

			String header = requestResult.getResponse().getHeader("bearer");
			assertEquals(header, generateToken);
		}
	}

	/**
	 * Test for new client request invalid question.
	 * 
	 * @throws Exception
	 */
	@Test
	void smartEquipHumanClientCheck_invalidQuestion() throws Exception {
		String clientRequest = "Hey Service, can you provide me a ques tion  with  numbers to  add ?";
		doThrow(new ValidationException(CommonConstants.WRONG_QUESTION)).when(validator).validateQuestion(any());
		MvcResult requestResult = this.mockMvc.perform(post("/").content(clientRequest))
				.andExpect(status().isBadRequest()).andReturn();
		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(CommonConstants.WRONG_QUESTION, response.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.name(), response.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	/**
	 * Test for client answer question in proper way
	 * 
	 * @throws Exception
	 */
	@Test
	void smartEquipHumanClientCheck_validAnswer() throws Exception {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
		when(validator.validateAnswer(any(), any())).thenReturn(smartequip);
		when(answersService.getServerAnswer(any())).thenReturn(CommonConstants.CORRECT_ANSWER);
		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceAnswer).header("bearer", generateToken))
				.andExpect(status().isOk()).andReturn();
		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(CommonConstants.CORRECT_ANSWER, response.getMessage());
		assertEquals(CommonConstants.SUCCESS, response.getStatus());
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	/**
	 * Test for client given wrong answer i.e, sum of numbers is incorrect.
	 * 
	 * @throws Exception
	 */
	@Test
	void smartEquipHumanClientCheck_invalidAnswer() throws Exception {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 10";
		doThrow(new ValidationException(CommonConstants.WRONG_ANSWER)).when(validator).validateAnswer(any(),
				any());
		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceAnswer).header("bearer", generateToken))
				.andExpect(status().isBadRequest()).andReturn();
		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(CommonConstants.WRONG_ANSWER, response.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.name(), response.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	/**
	 * Only For token exception testing. If any token creation error
	 * how it react.
	 * 
	 * @throws Exception
	 */
	@Test
	void tokenExceptionHandlerTest() throws Exception {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 10";
		doThrow(new TokenException(CommonConstants.TOKEN_CREATION_ERROR_MESSAGE)).when(validator).validateAnswer(any(),
				any());
		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceAnswer).header("bearer", generateToken))
				.andExpect(status().isInternalServerError()).andReturn();
		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(CommonConstants.TOKEN_CREATION_ERROR_MESSAGE, response.getMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getStatus());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
	}
	
	/**
	 * Only For global parent exception testing. If any exception not handled then
	 * how it react.
	 * 
	 * @throws Exception
	 */
	@Test
	void globalExceptionHandlerTest() throws Exception {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 10";
		doThrow(new NullPointerException()).when(validator).validateAnswer(any(), any());
		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceAnswer).header("bearer", generateToken))
				.andExpect(status().isInternalServerError()).andReturn();
		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(CommonConstants.INTERNAL_SERVER_ERROR_MESSAGE, response.getMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getStatus());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
	}

}
