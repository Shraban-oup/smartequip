package com.smartequip.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility methods registered here.
 * @author Shraban.Rana
 *
 */
@Component
public class CommonUtils {
	Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * This function generates random 2 digit no.
	 * Math.random() generates random number from 0.0 to 0.999
	 * Hence, Math.random()*99 will be from 0.0 to 99.999
	 * 
	 * @return
	 */
	public static int randomNumberUpToTwoDigit() {
		double doubleRandomNumber = Math.random() * 99;
		return (int) doubleRandomNumber;
	}
	
	/**
	 * This will give you digit >=2 and <= PropDetails.getMax()
	 * @return
	 */
	public static int maxDigitOfNumbers() {
		int max = PropDetails.getMax();
		double d = Math.random() * max;
		double doubleRandomNumber = 2+d;
		return (int) doubleRandomNumber;
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
	public static List<Integer> collectRandomNumbers() {
		int limit = maxDigitOfNumbers();
		return IntStream.range(0, limit).mapToObj(v -> CommonUtils.randomNumberUpToTwoDigit()).collect(Collectors.toList());
	}

	/**
	 * This function concatenate the List of value separated by delimiter.
	 * @param delimiter
	 * @param list
	 * @return String
	 */
	public static String getDelimiterSeparated(String delimiter, List<Integer> list) {
		return list.stream().map(String::valueOf).collect(Collectors.joining(delimiter));
	}

}
