package com.smartequip.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

@Component
public class MapperUtil {

	/**
	 * This function mapped to stored asked questions numbers and final answer.
	 * 
	 * @param generateRandomNumbers
	 * @param answer
	 * @return
	 */
	public Smartequip mapper(List<Integer> generateRandomNumbers) {
		int sumOfNumbers = CommonUtils.getSumOfNumbers(generateRandomNumbers);

		Smartequip smartequip = new Smartequip();
		smartequip.setQuestionNums(generateRandomNumbers);
		smartequip.setAnsewer(sumOfNumbers);
		return smartequip;
	}
}
