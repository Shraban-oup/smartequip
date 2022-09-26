package com.smartequip.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static int generateRandomNo() {
		try {
			double doubleRandomNumber = Math.random() * 99;
			int randomNumber = (int) doubleRandomNumber;
			return randomNumber;
		} catch (Exception e) {
			// If any issue while generate random no default no will send
			return CommonConstantsUtils.DEFAULT_NUMBER;
		}
	}

	public static List<Integer> extractAllDigits(String value) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(value);
		List<Integer> digits = new ArrayList<>();
		while (m.find()) {
			digits.add(Integer.parseInt(m.group()));
		}
		return digits;
	}

	public static List<Integer> generateRandomNumbers(int noOfDegit) {
		return IntStream.range(0, noOfDegit).mapToObj(v -> CommonUtils.generateRandomNo()).collect(Collectors.toList());
	}

	public static String getDelimiterSeparated(String delimiter, List list) {
		return (String) list.stream().map(v -> v.toString()).collect(Collectors.joining(","));
	}

	public static int getSumOfNumbers(List<Integer> numbers) {
		return numbers.stream().mapToInt(Integer::intValue).sum();
	}

}
