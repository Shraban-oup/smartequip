package com.smartequip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.cache.StoreInterface;
import com.smartequip.common.CommonConstantsUtils;
import com.smartequip.model.Smartequip;

@ExtendWith(MockitoExtension.class)
class SmartequipAnswersServiceTest {

	@Mock
	private StoreInterface storeInterface;

	@InjectMocks
	private SmartequipAnswersService answersService;

	@Test
	void getServerAnswerTest() {
		doNothing().when(storeInterface).deleteItem(any());
		assertEquals(CommonConstantsUtils.CORRECT_ANSWER, answersService.getServerAnswer(any()));
	}

	@Test
	void getSmartEquipDetailsTest() {
		List<Integer> questionNums = new ArrayList<>();
		questionNums.add(10);
		questionNums.add(5);
		questionNums.add(15);
		Smartequip smartequip = new Smartequip(questionNums, 30);
		when(storeInterface.getItem(any())).thenReturn(Optional.of(smartequip));
		assertEquals(smartequip.getAnsewer(), answersService.getSmartEquipDetails(any()).get().getAnsewer());
	}

}
