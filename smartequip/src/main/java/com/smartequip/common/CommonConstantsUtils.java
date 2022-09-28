package com.smartequip.common;

/**
 * This is the constant file
 * @author Shraban.Rana
 *
 */
public class CommonConstantsUtils {

	public static final String USER_FIRST_QUESTION_REGEX = "question\\swith\\snumbers\\sto\\sadd(\\s)?\\?";
	public static final String USER_ANSEWER_REGEX = "Please\\ssum\\sthe\\snumbers\\s\\d+,\\d+,\\d+.+and\\sthe\\sanswer\\sis\\s\\d+";

	public static final String SERVER_QUESTION_PREFIX = "Here you go, solve the question: Please sum the numbers ";
	public static final int DEFAULT_NUMBER = 5;
	public static final String SERVICE_ANSEWER_PREFIX = "the answer is";

	public static final String WRONG_QUESTION = "Please ask valid question or corrent the sentence";
	public static final String WRONG_ANSWER = "That's wrong. Please try again.";
	public static final String WRONG_ANSWER_FORMAT = "The sentence format is wrong, please check and corrent the sentence.Example: Your answer should contains following example format: 'Please sum the numbers 9,5,3' and the answer is 17.";
	public static final String CORRECT_ANSWER = "That's great";
	public static final String PRE_QUESTION_CHANGES = "please provide correct numbers";
	public static final String INVALID_TOEKN = "Invalid token";

	public static final String SUCCESS = "Success";
	public static final String COMMA = ",";

}
