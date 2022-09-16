package com.smartequip.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.smartequip.cache.StoreInterface;
import com.smartequip.model.Smartequip;

@ExtendWith(MockitoExtension.class)
class SmartequipService2Test {

	@Mock
	private StoreInterface storeInterface;

	@InjectMocks
	private SmartequipService2 service2;

	@Test
	public void validateAnswerTest() {
		Smartequip smartequip = new Smartequip("", 12);
		boolean result = service2.validateAnswer(
				"Sorry, the original question was “Please sum the numbers 1,4,7” and the answer is 12.", smartequip);
		Assertions.assertTrue(result);
	}

	@Test
	public void checkAnswerTest_invalidToken() {
		ResponseEntity<String> responseEntity = service2.checkAnswer("", "askjdklsjdks");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void checkAnswerTest_wrongSentence() {
		when(storeInterface.getItem(any())).thenReturn(Optional.of(new Smartequip("Please sum the numbers 1,4,7",12)));
		ResponseEntity<String> responseEntity = service2.checkAnswer("Sorry, the original question was “Please sum the numbers 1,4,7” and the   answer is 12.", "askjdklsjdks");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void checkAnswerTest_CorrectAnswer() {
		when(storeInterface.getItem(any())).thenReturn(Optional.of(new Smartequip("Please sum the numbers 1,4,7",12)));
		doNothing().when(storeInterface).deleteItem(any());
		ResponseEntity<String> responseEntity = service2.checkAnswer("Sorry, the original question was “Please sum the numbers 1,4,7” and the answer is 12.", "askjdklsjdks");
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void checkAnswerTest_WrongAnswer() {
		when(storeInterface.getItem(any())).thenReturn(Optional.of(new Smartequip("Please sum the numbers 1,4,7",12)));
		//Already in checkAnswerTest_CorrectAnswer doNothing implemented. so not required.
		//doNothing().when(storeInterface).deleteItem(any());
		ResponseEntity<String> responseEntity = service2.checkAnswer("Sorry, the original question was “Please sum the numbers 1,4,7” and the answer is 1325.", "assdkjdklsjdks");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
