package com.tgt.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.tgt.example.exception.InvalidDeleteRequestException;
import com.tgt.example.exception.InvalidUpdateRequestException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.RequestPreference;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.service.MarketingPerferenceService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UpdateController.class)
class UpdateControllerTest {
	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private MarketingPerferenceService perferenceService;

	@Test
	@DisplayName("update perference successsfully and response updated perference")
	void updatePerferenceTest() throws Exception {
		ResponsePerference expect = new ResponsePerference("0001", "my name", "post", null, null);
		Mockito.when(perferenceService.updatePerference(Mockito.anyString(), Mockito.any(RequestPreference.class))).thenReturn(expect);
        MvcResult mvnResponse = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/perferences/userId/{userId}", expect.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expect)))
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
	@DisplayName("Expect httpStatusCode BAD_REQUERST(400) with reason while updating invalid requesst")
	void updatePerferenceTestWithInvalidRequest() throws Exception {
		ResponsePerference request = new ResponsePerference("0001", "my name", "post", null, null);
		Mockito.when(perferenceService.updatePerference(Mockito.anyString(), Mockito.any(RequestPreference.class)))
			.thenThrow(new InvalidUpdateRequestException());
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/perferences/userId/{userId}", " ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid userId or empty request in post, email and sms field"));
    }

	@Test
	@DisplayName("delete perference successsfully and response deleted userId")
	void deletePerferenceTest() throws Exception {
		ResponsePerference expect = new ResponsePerference("0001");
		Mockito.when(perferenceService.deletePerference(Mockito.anyString())).thenReturn(expect);
        MvcResult mvnResponse = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/perferences/userId/{userId}", expect.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode result = objectMapper.readTree(mvnResponse.getResponse().getContentAsString());
        assertNotNull(result);
        assertNotNull(result.get("userId"));
        assertEquals(expect.getUserId(), result.get("userId").asText());
        assertNull(result.get("name"));
        assertNull(result.get("post"));
        assertNull(result.get("email"));
        assertNull(result.get("sms"));
    }

	@Test
	@DisplayName("Expect httpStatusCode BAD_REQUERST(400) with reason while deleting invalid userId")
	void deletePerferenceTestWithInvalidUserId() throws Exception {
		Mockito.when(perferenceService.deletePerference(Mockito.anyString())).thenThrow(new InvalidDeleteRequestException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/perferences/userId/{userId}", " ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Invalid userId"));
    }

	@Test
	@DisplayName("Expect httpStatusCode NOT_FOUND(404) with reason while deleting non-existed userId")
	void deletePerferenceTestWithNonExistedUserId() throws Exception {
		Mockito.when(perferenceService.deletePerference(Mockito.anyString())).thenThrow(new UserIdNotExistException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/perferences/userId/{userId}", "0002")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(status().reason("UserId does not exist"));
    }
}
