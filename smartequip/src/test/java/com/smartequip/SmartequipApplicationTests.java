package com.smartequip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartequipApplicationTest {

	@Test
	void contextLoads() {
		SmartequipApplication application = new SmartequipApplication();
		String[] value= {"org.springframework.boot.SpringApplication.SmartequipApplication"};
		application.main(value);
	}

}
