package com.smartequip.common;

/**
 * This is the constant file
 * 
 * @author Shraban.Rana
 *
 */
public class CommonConstants {

	public static final String USER_FIRST_QUESTION_REGEX = "^(H|h)ey\\s(S|s)ervice\\s?,\\s?can\\syou\\sprovide\\sme\\sa\\squestion\\swith\\snumbers\\sto\\sadd(\\s)?\\?";
	public static final String USER_ANSEWER_REGEX_PART1 = "Please\\ssum\\sthe\\snumbers\\s(\\d+\\s?,\\s?){";
	public static final String USER_ANSEWER_REGEX_PART2 = "}\\d+(\\D){1,2}and\\sthe\\sanswer\\sis\\s\\d+((\\D){1,2})?";

	public static final String INVALID_CLIENT_ANSWER_REQUEST = "Please request with correct numbers and valid token ";

	public static final String SERVER_QUESTION_PREFIX = "Here you go, solve the question: Please sum the numbers ";
	public static final int DEFAULT_NUMBER = 5;
	public static final String SERVICE_ANSEWER_PREFIX = "the answer is";

	public static final String WRONG_QUESTION = "Invalid syntax. Expected syntax is 'Hey Service, can you provide me a question with numbers to add ?'";
	public static final String WRONG_ANSWER = "That's wrong. Please try again.";
	public static final String WRONG_ANSWER_FORMAT = "Syntax is wrong. Example: Your answer should end with following syntax: 'Please sum the numbers 9,5,3' and the answer is 17.";
	public static final String CORRECT_ANSWER = "That's great";
	public static final String PRE_QUESTION_CHANGES = "please provide correct numbers";
	public static final String INVALID_TOEKN = "Invalid token";

	public static final String SUCCESS = "Success";
	public static final String COMMA = ",";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Sorry, something went wrong. Please try again";
	public static final String TOKEN_CREATION_ERROR_MESSAGE = "Sorry, something went wrong. Please try again";
	public static final String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";

	private CommonConstants() {}
	

}
