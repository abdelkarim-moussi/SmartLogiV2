package com.app.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration.class
})
class SmartLogiV2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
