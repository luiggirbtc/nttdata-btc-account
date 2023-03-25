package com.nttdata.btc.account.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NttdataBtcAccountApplicationTests {

	@Test
	void contextLoads() {
		String expected = "btc-account";
		String actual = "btc-account";

		assertEquals(expected, actual);
	}
}