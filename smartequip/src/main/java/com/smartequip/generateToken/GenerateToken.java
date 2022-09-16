package com.smartequip.generateToken;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

/*This is very simple token generate for validation purpose that previous user and current user is same or not.
We can improve it by other string token creation in java and spring*/
public class GenerateToken {

	public Optional<String> generateToekn() {

		Supplier<String> tokenSupplier = () -> {

			StringBuilder token = new StringBuilder();
			long currentTimeInMilisecond = Instant.now().toEpochMilli();
			return token.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();
		};

		return Stream.generate(tokenSupplier).limit(1).findFirst();

	}
}
