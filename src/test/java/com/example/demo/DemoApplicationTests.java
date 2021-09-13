package com.example.demo;

import com.example.demo.schedulingtasks.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private ScheduledTasks tasks;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
		// Basic integration test that shows the context starts up properly
		assertThat(tasks).isNotNull();
		assertThat(restTemplate).isNotNull();
	}

	// Add tests for validating forms.
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkPersonInfoWhenNameMissingThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("age", "20");

		mockMvc.perform(createPerson).andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenNameTooShortThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "R")
				.param("age", "20");

		mockMvc.perform(createPerson).andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenAgeMissingThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "Rob");

		mockMvc.perform(createPerson).andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenValidRequestThenSuccess() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "Rob")
				.param("age", "20");

		mockMvc.perform(createPerson).andExpect(model().hasNoErrors());
	}
}
