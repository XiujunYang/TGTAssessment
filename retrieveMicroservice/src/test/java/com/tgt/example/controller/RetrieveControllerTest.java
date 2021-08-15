package com.tgt.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.example.exception.InvalidUserIdException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.service.MarketingPerferenceService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RetrieveController.class)
class RetrieveControllerTest {
	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private MarketingPerferenceService perferenceService;

	@Test
	@DisplayName("retrieve perference successsfully and response perference")
	void updatePerferenceTest() throws Exception {
		ResponsePerference expect = new ResponsePerference("0001", "my name", "post", null, null);
		Mockito.when(perferenceService.getPerference(Mockito.anyString())).thenReturn(expect);
        MvcResult mvnResponse = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/perferences/userId/{userId}", expect.getUserId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode result = objectMapper.readTree(mvnResponse.getResponse().getContentAsString());
        assertNotNull(result);
        assertNotNull(result.get("userId"));
        assertNotNull(result.get("name"));
        assertNotNull(result.get("post"));
        assertEquals(expect.getUserId(), result.get("userId").asText());
        assertEquals(expect.getName(), result.get("name").asText());
        assertEquals(expect.getPost(), result.get("post").asText());
        assertNull(result.get("email"));
        assertNull(result.get("sms"));
    }

	@Test
	@DisplayName("Expect httpStatusCode BAD_REQUERST(400) with reason while retreiving perference by invalid userId")
	void updatePerferenceTestWithInvalidUserId() throws Exception {
		Mockito.when(perferenceService.getPerference(Mockito.anyString())).thenThrow(new InvalidUserIdException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/perferences/userId/{userId}", " ")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid userId"));
    }

	@Test
	@DisplayName("Expect httpStatusCode NOT_FOUND(404) with reason while retrieving perference by non-existed userId")
	void updatePerferenceTestWithNonExistedUserId() throws Exception {
		Mockito.when(perferenceService.getPerference(Mockito.anyString())).thenThrow(new UserIdNotExistException());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/perferences/userId/{userId}", "0001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(status().reason("UserId does not exist"));
    }
}
