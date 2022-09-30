package com.smartequip.tokengenerator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.smartequip.common.CommonConstants;
import com.smartequip.exceptionhandler.TokenException;

/**
 * @author Shraban.Rana
 *
 */
@Component
public class TokenGenerator {

	/**
	 * This is generate unique token based on currentTimeInMilisecond and hash code.
	 * 
	 * @param randomNumbers
	 * @return String
	 */
	public String createUniqueToken(List<Integer> randomNumbers) {
		Optional<String> randomToken = getRandomToken();
		if (randomToken.isPresent()) {
			return randomToken.get() + randomNumbers.hashCode();
		}
		throw new TokenException(CommonConstants.TOKEN_CREATION_ERROR_MESSAGE);
	}

	public Optional<String> getRandomToken() {
		Supplier<String> tokenSupplier = () -> {
			StringBuilder token = new StringBuilder();
			long currentTimeInMilisecond = Instant.now().toEpochMilli();
			return token.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();
		};

		return Stream.generate(tokenSupplier).limit(1).findFirst();

	}
}
