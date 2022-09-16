package com.smartequip.common;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

	public static int generateRandomNo() {
		try {
			double doubleRandomNumber = Math.random() * 99;
			int randomNumber = (int) doubleRandomNumber;
			return randomNumber;
		} catch (Exception e) {
			// If any issue while generate random no default no will send
			return CommonConstantUtis.DEFAULT_NUMBER;
		}
	}

	public static Optional<Integer> extractNumbers(String s) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(s);

		if (m.find()) {
			return Optional.of(Integer.parseInt(m.group()));
		}
		return Optional.empty();
	}

}
