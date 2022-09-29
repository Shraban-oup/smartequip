package com.smartequip.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.common.MapperUtil;
import com.smartequip.exceptionhandler.ResourceNotFoundException;
import com.smartequip.exceptionhandler.ValidationException;
import com.smartequip.generateToken.TokenGenerator;
import com.smartequip.model.ErrorMessage;
import com.smartequip.model.Smartequip;
import com.smartequip.model.SmartequipResponse;
import com.smartequip.service.SmartequipAnswersService;
import com.smartequip.service.SmartequipQuestionsService;
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
	
	@BeforeAll
	public static void init() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		smartequip = new Smartequip(questionNums, 30);
		generateToken = new TokenGenerator().generateToken(smartequip);
	}
	
	/**
	 * This function used for success scenario of client new request
	 * 
	 * @throws Exception
	 */
	@Test
	void newClientValidQuestion() throws Exception {
		String serviceQuestion = "Here you go, solve the question: Please sum the numbers 10,4,6.";
		String clientRequest = "Hey Service, can you provide me a question with numbers to add ?";
		when(this.tokenGenerator.generateToken(any())).thenReturn(generateToken);
		MvcResult requestResult = this.mockMvc.perform(post("/").content(clientRequest)).andExpect(status().isOk())
				.andReturn();

		String header = requestResult.getResponse().getHeader("bearer");
		assertEquals(header, generateToken);

	}


	/**
	 * This Function return bad request if client asked question has extra space.so wrong sentence. 
	 * expected:         "Hey Service, can you provide me a question with numbers to add ?"; 
	 * client requested: "Hey Service, can you provide me a question with  numbers to add ?";
	 * 
	 * @throws Exception
	 */
	@Test
	void newClientWrongQuestion_extraspace() throws Exception {
		String clientRequest = "Hey Service, can you provide me a question with  numbers to add ?";

		doThrow(new ValidationException(CommonConstantsUtils.WRONG_QUESTION)).when(this.validator)
				.validateQuestion(any());

		MvcResult requestResult = this.mockMvc.perform(post("/").content(clientRequest))
				.andExpect(status().isBadRequest()).andReturn();

		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(response.getMessage(), CommonConstantsUtils.WRONG_QUESTION);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());

	}

	/**
	 * This Function return bad request if client asked question is not proper.
	 * expected:         "Hey Service, can you provide me a question with numbers to add ?";
	 * client requested: "Hey Service, can you provide me a question to add ?"
	 * 
	 * @throws Exception
	 */
	@Test
	void newClientWrongQuestion_missingWord() throws Exception {
		String clientRequest = "Hey Service, can you provide me a question with to add ?";

		doThrow(new ValidationException(CommonConstantsUtils.WRONG_QUESTION)).when(this.validator)
				.validateQuestion(any());

		MvcResult requestResult = this.mockMvc.perform(post("/").content(clientRequest))
				.andExpect(status().isBadRequest()).andReturn();

		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(response.getMessage(), CommonConstantsUtils.WRONG_QUESTION);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());

	}

	/**
	 * Given correct answer and correct token. Success case.
	 * 
	 * @throws Exception
	 */
	@Test
	void oldUserRequest_corretAnswer() throws Exception {
		String serviceAnswer = "Great. The original question was “Please sum the numbers 10,5,15” and the answer is 30";
		String token = new TokenGenerator().generateToken(smartequip);
		when(this.answersService.getServerAnswer(any())).thenReturn(CommonConstantsUtils.CORRECT_ANSWER);

		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceAnswer).header("bearer", token))
				.andExpect(status().isOk()).andReturn();

		String json = requestResult.getResponse().getContentAsString();

		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
		assertEquals(response.getMessage(), CommonConstantsUtils.CORRECT_ANSWER);
		assertEquals(response.getStatus(), "Success");
		assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}

	/**
	 * OldUser answer the question but provide wrong token. 
	 * @throws Exception
	 */
	@Test
	void oldUserRequest_invalidToken() throws Exception {
		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15";
		String token = "kjasbdkjbak23292";
		doThrow(new ValidationException(CommonConstantsUtils.INVALID_TOEKN)).when(this.validator)
		.validateAnswer(any(), any());

		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
				.andExpect(status().isBadRequest()).andReturn();

		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(response.getMessage(), CommonConstantsUtils.INVALID_TOEKN);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	/**
	 * OldUser answer the question but provide wrong token. 
	 * @throws Exception
	 */
	@Test
	void oldUserRequest_tokenNotExitsDueToSomeResason() throws Exception {
		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15";
		String token = "kjasbdkjbak23292";
		doThrow(new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString())).when(this.validator)
		.validateAnswer(any(), any());

		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
				.andExpect(status().isNotFound()).andReturn();

		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(response.getMessage(), HttpStatus.NOT_FOUND.toString());
		assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.name());
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND.value());
	}
	
	/**
	 * OldUser answer the question but provide wrong token. 
	 * @throws Exception
	 */
	@Test
	void oldUserRequest_parentException() throws Exception {
		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15";
		String token = "kjasbdkjbak23292";
		doThrow(new NullPointerException("rendom exceptio test")).when(this.validator)
		.validateAnswer(any(), any());

		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
				.andExpect(status().isInternalServerError()).andReturn();

		String json = requestResult.getResponse().getContentAsString();
		ErrorMessage response = new ObjectMapper().readValue(json, ErrorMessage.class);
		assertEquals(response.getMessage(), "rendom exceptio test");
		assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.name());
		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
//	
//	
//	/**
//	 * This Function return bad request if client asked question format is wrong. extra space.
//	 * expected:         "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15";
//	 * client requested: "Great. The original question was “Please sum the  numbers 9, 5,3” and the answer   is 15";
//
//	 * @throws Exception
//	 */
//	@Test
//	void oldUserRequest_wrongformat() throws Exception {
//		String serviceQuestion = "Great. The original question was 'Please sum the  numbers 9, 5,3' and the answer   is 15";
//		String token = new TokenGenerator().generateToken();
//		when(this.answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(new Smartequip()));
//		when(this.validator.validateAnswer(any(), any()))
//				.thenReturn(new Validator().validateAnswer(serviceQuestion, Optional.of(new Smartequip())));
//
//		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
//				.andExpect(status().isBadRequest()).andReturn();
//
//		String json = requestResult.getResponse().getContentAsString();
//		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
//		assertEquals(response.getMessage(), CommonConstantsUtils.WRONG_ANSWER_FORMAT);
//		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
//		assertEquals(response.getStatus_code(), HttpStatus.BAD_REQUEST.value());
//	}
//	
//	/**
//	 * This Function return bad request if client asked question with changes number.
//	 * expected:         "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 17";
//	 * client requested: "Great. The original question was “Please sum the numbers 9,10,8” and the answer is 17";
//
//	 * @throws Exception
//	 */
//	@Test
//	void oldUserRequest_numberChange() throws Exception {
//		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,10,8” and the answer is 15";
//		String token = new TokenGenerator().generateToken();
//		List<Integer> questionNums=new  ArrayList<>();
//		questionNums.add(9);
//		questionNums.add(5);
//		questionNums.add(3);
//		Smartequip smartequip=new Smartequip(questionNums, 30);
//		when(this.answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(smartequip));
//		when(this.validator.validateAnswer(any(), any()))
//				.thenReturn(new Validator().validateAnswer(serviceQuestion, Optional.of(smartequip)));
//
//		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
//				.andExpect(status().isBadRequest()).andReturn();
//
//		String json = requestResult.getResponse().getContentAsString();
//		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
//		assertEquals(response.getMessage(), CommonConstantsUtils.PRE_QUESTION_CHANGES);
//		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
//		assertEquals(response.getStatus_code(), HttpStatus.BAD_REQUEST.value());
//	}
//	
//	/**
//	 * This Function return bad request if client asked question with change number.
//	 * expected:         "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 17";
//	 * client requested: "Great. The original question was “Please sum the numbers 9,5,3,1” and the answer is 17";
//
//	 * @throws Exception
//	 */
//	@Test
//	void oldUserRequest_extraNumber() throws Exception {
//		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5,3,1” and the answer is 17";
//		String token = new TokenGenerator().generateToken();
//		List<Integer> questionNums=new  ArrayList<>();
//		questionNums.add(9);
//		questionNums.add(5);
//		questionNums.add(3);
//		Smartequip smartequip=new Smartequip(questionNums, 30);
//		when(this.answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(smartequip));
//		when(this.validator.validateAnswer(any(), any()))
//				.thenReturn(new Validator().validateAnswer(serviceQuestion, Optional.of(smartequip)));
//
//		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
//				.andExpect(status().isBadRequest()).andReturn();
//
//		String json = requestResult.getResponse().getContentAsString();
//		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
//		assertEquals(response.getMessage(), CommonConstantsUtils.PRE_QUESTION_CHANGES);
//		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
//		assertEquals(response.getStatus_code(), HttpStatus.BAD_REQUEST.value());
//	}
//	
//	/**
//	 * This Function return bad request if client asked question with missing number.
//	 * expected:         "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 17";
//	 * client requested: "Great. The original question was “Please sum the numbers 9,5” and the answer is 17";
//
//	 * @throws Exception
//	 */
//	@Test
//	void oldUserRequest_missingNumber() throws Exception {
//		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5” and the answer is 17";
//		String token = new TokenGenerator().generateToken();
//		List<Integer> questionNums=new  ArrayList<>();
//		questionNums.add(9);
//		questionNums.add(5);
//		questionNums.add(3);
//		Smartequip smartequip=new Smartequip(questionNums, 30);
//		when(this.answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(smartequip));
//		when(this.validator.validateAnswer(any(), any()))
//				.thenReturn(new Validator().validateAnswer(serviceQuestion, Optional.of(smartequip)));
//
//		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
//				.andExpect(status().isBadRequest()).andReturn();
//
//		String json = requestResult.getResponse().getContentAsString();
//		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
//		assertEquals(response.getMessage(), CommonConstantsUtils.WRONG_ANSWER_FORMAT);
//		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
//		assertEquals(response.getStatus_code(), HttpStatus.BAD_REQUEST.value());
//	}
//	
//	/**
//	 * This Function return bad request if client given wrong answer.
//	 * expected:         "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 17";
//	 * client requested: "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 19";
//
//	 * @throws Exception
//	 */
//	@Test
//	void oldUserRequest_wrongAnswer() throws Exception {
//		String serviceQuestion = "Great. The original question was “Please sum the numbers 9,5,3” and the answer is 17";
//		String token = new TokenGenerator().generateToken();
//		List<Integer> questionNums=new  ArrayList<>();
//		questionNums.add(9);
//		questionNums.add(5);
//		questionNums.add(3);
//		Smartequip smartequip=new Smartequip(questionNums, 30);
//		when(this.answersService.getSmartEquipDetails(any())).thenReturn(Optional.of(smartequip));
//		when(this.validator.validateAnswer(any(), any()))
//				.thenReturn(new Validator().validateAnswer(serviceQuestion, Optional.of(smartequip)));
//
//		MvcResult requestResult = this.mockMvc.perform(post("/").content(serviceQuestion).header("bearer", token))
//				.andExpect(status().isBadRequest()).andReturn();
//
//		String json = requestResult.getResponse().getContentAsString();
//		SmartequipResponse response = new ObjectMapper().readValue(json, SmartequipResponse.class);
//		assertEquals(response.getMessage(), CommonConstantsUtils.WRONG_ANSWER);
//		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.name());
//		assertEquals(response.getStatus_code(), HttpStatus.BAD_REQUEST.value());
//	}
//	

}
