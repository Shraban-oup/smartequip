package com.smartequip.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods registered here.
 * @author Shraban.Rana
 *
 */
public class CommonUtils {
	Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * This function generates random 2 digit no.
	 * Math.random() generates random number from 0.0 to 0.999
	 * Hence, Math.random()*99 will be from 0.0 to 99.999
	 * 
	 * @return int
	 */
	public static int generateSingleRandomNo() {
		double doubleRandomNumber = Math.random() * 99;
		int randomNumber = (int) doubleRandomNumber;
		return randomNumber;
	}

	/**
	 * This will fetch all digit from a sentence.
	 * @param value String type
	 * @return List<Integer>
	 */
	public static List<Integer> extractAllDigits(String value) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(value);
		List<Integer> digits = new ArrayList<>();
		while (m.find()) {
			digits.add(Integer.parseInt(m.group()));
		}
		return digits;
	}

	/**
	 * This function give you list of random no as per need.
	 * @param noOfDegit
	 * @return List<Integer>
	 */
	public static List<Integer> generateRandomNumbers(int noOfDegit) {
		return IntStream.range(0, noOfDegit).mapToObj(v -> CommonUtils.generateSingleRandomNo()).collect(Collectors.toList());
	}

	/**
	 * This function concatenate the List of value separated by delimiter.
	 * @param delimiter
	 * @param list
	 * @return String
	 */
	public static String getDelimiterSeparated(String delimiter, List list) {
		return (String) list.stream().map(v -> v.toString()).collect(Collectors.joining(","));
	}

	/**
	 * This function find sum of List of integer.
	 * @param numbers
	 * @return final sum
	 */
	public static int getSumOfNumbers(List<Integer> numbers) {
		return numbers.stream().mapToInt(Integer::intValue).sum();
	}

}
