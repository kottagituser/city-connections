package com.mc.code.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.graph.ImmutableGraph;
import com.mc.code.challenge.controller.CityConnectorController;

@SpringBootTest
@AutoConfigureMockMvc
class McCodeChallengeApplicationTests {
	
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final String ORIGIN = "origin";
	private static final String DESTINATION = "destination";
	private static final String AUTHORIZATION = "Authorization";
	private static final String AUTH = "Basic bWFzdGVyY2FyZDptYXN0ZXJjYXJk";

	private final String TEST_URI = "/connected";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	@Qualifier("cityConnections")
	private ImmutableGraph<String> cityGraph;

	@Autowired
	private CityConnectorController controller;

	@Test
	void contextLoads() {
		
		assertThat(controller).isNotNull();
		
		assertThat(cityGraph).isNotNull();
		assertThat(cityGraph.nodes().size() > 0);
		assertThat(cityGraph.edges().size() > 0);
	}
	
	@Test
	public void immutableGraph() throws Exception {

		Assertions.assertThrows(UnsupportedOperationException.class, () -> cityGraph.nodes().clear());
	}

	@Test
	public void Not_Authorized_401() throws Exception {

		mockMvc.perform(get(TEST_URI)
				// .header(AUTHORIZATION, AUTH)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status()
				.isUnauthorized()).andDo(print());
	}
	
	@Test
	public void Missing_Origin_Param() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(DESTINATION, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	
	@Test
	public void Missing_Destination_Param() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Missing_Origin_And_Destination_Params() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Missing_Origin_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "")
				.queryParam(DESTINATION, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Missing_Destination_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, val)
				.queryParam(DESTINATION, "")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Missing_Origin_And_Destination_Param_Values() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "")
				.queryParam(DESTINATION, "")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Blank_Origin_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "   ")
				.queryParam(DESTINATION, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Blank_Destination_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, val)
				.queryParam(DESTINATION, "	")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void Blank_Origin_And_Destination_Param_Values() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "  ")
				.queryParam(DESTINATION, " ")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void NotInFile_Origin_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "VALQWE")
				.queryParam(DESTINATION, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void NotInFile_Destination_Param_Value() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, val)
				.queryParam(DESTINATION, "QWERTY")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void NotInFile_Origin_And_Destination_Param_Values() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "ABCD")
				.queryParam(DESTINATION, "WXYZ")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void InFile_Origin_And_Destination_Param_Values_Same() throws Exception {

		String val = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, val)
				.queryParam(DESTINATION, val)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(YES))
				.andDo(print());
	}
	
	@Test
	public void InFile_Connected_Origin_And_Destination_Param_Values_One_Way() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "newark")
				.queryParam(DESTINATION, "boston")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(YES))
				.andDo(print());
	}
	
	@Test
	public void InFile_Connected_Origin_And_Destination_Param_Values_Other_Way() throws Exception {

		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "boston")
				.queryParam(DESTINATION, "newark")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(YES))
				.andDo(print());
	}
	
	@Test
	public void InFile_Not_Connected_Origin_And_Destination_Param_Values_One_Way() throws Exception {

		String origin = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, origin)
				.queryParam(DESTINATION, "nantucket")
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
	@Test
	public void InFile_Not_Connected_Origin_And_Destination_Param_Values_Other_Way() throws Exception {

		String origin = cityGraph.nodes().iterator().next();
		mockMvc.perform(get(TEST_URI)
				.queryParam(ORIGIN, "nantucket")
				.queryParam(DESTINATION, origin)
				.header(AUTHORIZATION, AUTH)
				.accept(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().string(NO))
				.andDo(print());
	}
	
}
