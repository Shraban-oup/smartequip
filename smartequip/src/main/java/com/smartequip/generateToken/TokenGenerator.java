package com.smartequip.generateToken;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

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

		Supplier<String> tokenSupplier = () -> {
			StringBuilder token = new StringBuilder();
			long currentTimeInMilisecond = Instant.now().toEpochMilli();
			return token.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();
		};

		String token = Stream.generate(tokenSupplier).limit(1).findFirst().get();
		return token+randomNumbers.hashCode();

	}
}
