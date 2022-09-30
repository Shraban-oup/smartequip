package com.smartequip.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Shraban.Rana
 *
 */
@Component
public class PropDetails {

	/**
	 * The reason been this field is static is, it can use anywhere. As this is one
	 * time setup in properties file.
	 */
	private static String digitMax;

	/**
	 * This is modifies to get Integer type.
	 * 
	 * @return
	 */
	public static int getMax() {
		return Integer.valueOf(digitMax);
	}

	/**
	 * @param digitMax
	 */
	@Value("${digit.max}")
	public void setDigitMax(String digitMax) {
		PropDetails.digitMax = digitMax;
	}
}
