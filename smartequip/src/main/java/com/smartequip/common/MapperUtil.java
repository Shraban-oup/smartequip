package com.smartequip.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@Component
public class MapperUtil {



	/**
	 * This function mapped to stored asked questions numbers and token.
	 * @param numers
	 * @param token
	 * @return
	 */
	public Smartequip mapper(List<Integer> numers, String token) {
		Smartequip smartequip = new Smartequip();
		smartequip.setQuestionNums(numers);
		smartequip.setUniqueToken(token);
		return smartequip;
	}
}
