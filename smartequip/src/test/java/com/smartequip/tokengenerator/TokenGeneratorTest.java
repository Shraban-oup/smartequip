package com.smartequip.tokengenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartequip.common.CommonConstants;
import com.smartequip.exceptionhandler.TokenException;

/**
 * @author Shraban.Rana
 *
 */
@ExtendWith(MockitoExtension.class)
class TokenGeneratorTest {

	@Spy
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
		when(generator.getRandomToken()).thenReturn(Optional.of("12345dsdfdf"));
		String createUniqueToken = generator.createUniqueToken(digitList);
		assertFalse(StringUtils.isEmpty(createUniqueToken));
	}

	@Test
	void createUniqueTokenTest_Failed() {
		when(generator.getRandomToken()).thenReturn(Optional.empty());
		assertThrows(TokenException.class, () -> generator.createUniqueToken(digitList), CommonConstants.TOKEN_CREATION_ERROR_MESSAGE);
	}

	@Test
	void getRandomTokenTest() {
		Optional<String> randomToken = generator.getRandomToken();
		assertEquals(true, randomToken.isPresent());
	}
}
