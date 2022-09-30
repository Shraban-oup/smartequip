package com.smartequip.generateToken;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class TokenGeneratorTest {

	@InjectMocks
	private TokenGenerator generator;

	private static List<Integer> digitList;

	@BeforeAll
	public static void init() {
		digitList = new ArrayList<>();
		digitList.add(10);
		digitList.add(5);
		digitList.add(15);
	}

	/**
	 * This test will verify that token generated correctly
	 */
	@Test
	void createUniqueTokenTest() {
		String createUniqueToken = generator.createUniqueToken(digitList);
		assertFalse(StringUtils.isEmpty(createUniqueToken));
	}

}
