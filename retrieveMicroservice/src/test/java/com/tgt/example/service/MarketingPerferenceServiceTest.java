package com.tgt.example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import com.tgt.example.dao.MarketingPerferenceDAO;
import com.tgt.example.exception.InvalidUserIdException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.repository.PerferenceRepository;

@SpringBootTest()
class MarketingPerferenceServiceTest {
	@Autowired
    private MarketingPerferenceService service;
	@MockBean
	private PerferenceRepository repository;
	
	@Configuration
	  @Import(MarketingPerferenceService.class)
	  static class TestConfig {
	    @Bean
	    PerferenceRepository userRepository() {
	      return Mockito.mock(PerferenceRepository.class);
	    }
	  }

	@Test
	@DisplayName("Test private method isValidUpdatedRequest with invalid userId")
	void isValidUpdatedRequestTestWithInvalidUserId() {
		boolean result = ReflectionTestUtils.invokeMethod(service, "isValidUpdatedRequest", " ");
		assertFalse(result);
	}

	@Test
	@DisplayName("Test private method isValidUpdatedRequest with valid request")
	void isValidUpdatedRequestTest() {
		boolean result = ReflectionTestUtils.invokeMethod(service, "isValidUpdatedRequest", "0001");
		assertTrue(result);
	}

	private boolean isEquals(MarketingPerferenceDAO expect, ResponsePerference result) {
		if(expect != null && result != null && expect.getUserId() == result.getUserId() && expect.getName() == result.getName()
				&& expect.getPost() == result.getPost() && expect.getEmail() == result.getEmail() && expect.getSms() == result.getSms())
			return true;
		return false;
	}

	@Test
	@DisplayName("Retreive perference successfully")
	void updatePerferenceTest() {
		MarketingPerferenceDAO expect =  new MarketingPerferenceDAO("0001", "Mr./Ms. 0001","post pattern", null, "sms pattern", false);
		Mockito.when(repository.findByUserIdAndDeleted(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(expect);
		ResponsePerference result = service.getPerference(expect.getUserId());
		assertNotNull(result);
		assertTrue(isEquals(expect, result));
	}

	@Test
	@DisplayName("Throw InvalidUserIdException when request is invalid")
	void updatePerferenceWithInvalidReqTest() {
		assertThrows(InvalidUserIdException.class, () -> {service.getPerference(" ");});
	}

	@Test
	@DisplayName("Thrrow UserIdNotExistException when userId does not exist")
	void updatePerferenceWhenDatabaseFailed() {
		Mockito.when(repository.findByUserIdAndDeleted(Mockito.anyString(),Mockito.anyBoolean())).thenReturn(null);
		assertThrows(UserIdNotExistException.class, () -> {service.getPerference("0001");});
	}
}
