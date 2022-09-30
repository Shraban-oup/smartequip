package com.smartequip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.cache.StoreProcess;
import com.smartequip.common.CommonConstantsUtils;

/**
 * Test for client answer service class 
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class SmartequipAnswersServiceTest {

	@Mock
	private StoreProcess storeInterface;

	@InjectMocks
	private SmartequipAnswersService answersService;

	/**
	 * Test for server final response is correctly coming or not.
	 */
	@Test
	void getServerAnswerTest() {
		doNothing().when(storeInterface).deleteItem(any());
		assertEquals(CommonConstantsUtils.CORRECT_ANSWER, answersService.getServerAnswer(any()));
	}

	/**
	 * Test for expected answer of asked question is correctly coming from server
	 */
	@Test
	void getSmartEquipDetailsTest() {
		when(storeInterface.getItem(any())).thenReturn(Optional.of(12));
		assertEquals(12, answersService.getSmartEquipDetails(any()).get());
	}
}
