package com.jpmorgan.restdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SimpleRestApiApplicationTests {

	// TODO: write more tests
	/* TODO: a list of tests I would write if I knew how to (researching this would take too much time as many of the tests I tried to write failed)
		test database is initialised with correct values.
		test repository retrieves correct values for the various methods (possibly unecessary given it's just extending the jpa repository.
		test text on front end correct. * see below for an example that I couldn't get working despite several different flavours of it.
		test fields appear on front end.
		test errors are shown.
	 */

	@LocalServerPort
	private int port;

	@Autowired
	private HomeController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	private String url = "http://localhost:" + port;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	/**
	 * I was get a template exceptions when I tried to do this as a pure MVC Test but I found this alternative.
	 * @throws Exception

	@Test
	void indexContainsRelevantText() throws Exception {
		String expected1 = "This page is a demonstration of a Rest API made using SpringBoot, Java and Maven.";
		String expected2 = "It calls a Rest API to retrieve the data from the database.";
		String content = this.restTemplate.getForObject(url + "/", String.class);
		assertThat(content).contains(expected1);
		assertThat(content).contains(expected2);
	}*/



}
