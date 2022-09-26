package com.smartequip.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SmartequipQuestionsServiceTest {
//
//	@InjectMocks
//	private SmartequipQuestionsService service1;
//	
//	@Mock
//	private static Smartequip smartequip;
//	
//	@Mock
//	private StoreInterface storeInterface;
//	
//	@Mock
//	private GenerateToken generateToken;
//	
//	@Test
//	public void mapperTest() {
//		smartequip = new Smartequip("Please sum the numbers 87,54,82", 223);
//		Smartequip createCreditCard = service1.mapper(87, 54, 82, "Please sum the numbers 87,54,82",new Smartequip());
//		Assertions.assertEquals(smartequip.getAnsewer(), createCreditCard.getAnsewer());
//	}
//	
//	@Test
//	public void getInitResponseTest_wrongSentence() {
//		ResponseEntity<String> responseEntity=service1.getInitResponse("Hey Service, can you provide me a     question with    numbers to add?");
//		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//	}
//	
//	@Test
//	public void getInitResponseTest_positive() {
//		when(generateToken.generateToekn()).thenReturn(Optional.of("123"));
//		doNothing().when(storeInterface).addItem(any(), any());
//		ResponseEntity<String> responseEntity=service1.getInitResponse("Hey Service, can you provide me a question with numbers to add?");
//		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//	}
}
