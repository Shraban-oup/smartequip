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
import com.smartequip.generateToken.GenerateToken;
import com.smartequip.model.Smartequip;

@ExtendWith(MockitoExtension.class)
class SmartequipService1Test {

	@InjectMocks
	private SmartequipService1 service1;
	
	@Mock
	private static Smartequip smartequip;
	
	@Mock
	private StoreInterface storeInterface;
	
	@Mock
	private GenerateToken generateToken;
	
	@Test
	public void mapperTest() {
		smartequip = new Smartequip("Please sum the numbers 87,54,82", 223);
		Smartequip createCreditCard = service1.mapper(87, 54, 82, "Please sum the numbers 87,54,82",new Smartequip());
		Assertions.assertEquals(smartequip.getAnsewer(), createCreditCard.getAnsewer());
	}
	
	@Test
	public void getInitResponseTest_wrongSentence() {
		ResponseEntity<String> responseEntity=service1.getInitResponse("Hey Service, can you provide me a     question with    numbers to add?");
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void getInitResponseTest_positive() {
		when(generateToken.generateToekn()).thenReturn(Optional.of("123"));
		doNothing().when(storeInterface).addItem(any(), any());
		ResponseEntity<String> responseEntity=service1.getInitResponse("Hey Service, can you provide me a question with numbers to add?");
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
