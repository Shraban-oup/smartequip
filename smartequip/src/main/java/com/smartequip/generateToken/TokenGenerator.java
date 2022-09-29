package com.smartequip.generateToken;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.smartequip.model.Smartequip;

/**
 * @author Shraban.Rana
 *
 */
@Component
public class TokenGenerator {

	/**
	 * This is generate unique token based on currentTimeInMilisecond and hash code.
	 * 
	 * @param smartequip
	 * @return String
	 */
	public String generateToken(Smartequip smartequip) {

		Supplier<String> tokenSupplier = () -> {
			StringBuilder token = new StringBuilder();
			long currentTimeInMilisecond = Instant.now().toEpochMilli();
			return token.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();
		};

		String token = Stream.generate(tokenSupplier).limit(1).findFirst().get();
		return (String.valueOf(smartequip.hashCode()) + token);

	}
}
