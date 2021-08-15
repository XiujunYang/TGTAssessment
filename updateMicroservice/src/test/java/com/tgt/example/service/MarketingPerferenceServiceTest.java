package com.tgt.example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.tgt.example.exception.InvalidDeleteRequestException;
import com.tgt.example.exception.InvalidUpdateRequestException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.RequestPreference;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.repository.PerferenceRepository;

@SpringBootTest
public class MarketingPerferenceServiceTest {
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
	@DisplayName("Test private method isValidUpdatedRequest with blank userId")
	void isValidUpdatedRequestTestWithBlankUserId() {
		RequestPreference testReq = new RequestPreference();
		testReq.setEmail("Mr. Sir: would you...");
		boolean result = ReflectionTestUtils.invokeMethod(service, "isValidUpdatedRequest", " ", testReq);
		assertFalse(result);
	}

	@Test
	@DisplayName("Test private method isValidUpdatedRequest without any post, email and sms information")
	void isValidUpdatedRequestTestWithEmptyPostEmailSms() {
		RequestPreference testReq = new RequestPreference();
		testReq.setName("test name");
		boolean result = ReflectionTestUtils.invokeMethod(service, "isValidUpdatedRequest", "0001", testReq);
		assertFalse(result);
	}

	@Test
	@DisplayName("Test private method isValidUpdatedRequest with valid request")
	void isValidUpdatedRequestTest() {
		RequestPreference testReq = new RequestPreference();
		testReq.setEmail("Mr. Sir: would you...");
		boolean result = ReflectionTestUtils.invokeMethod(service, "isValidUpdatedRequest", "0001", testReq);
		assertTrue(result);
	}

	private boolean isEquals(MarketingPerferenceDAO expect, ResponsePerference result) {
		if(expect != null && result != null && expect.getUserId() == result.getUserId() && expect.getName() == result.getName()
				&& expect.getPost() == result.getPost() && expect.getEmail() == result.getEmail() && expect.getSms() == result.getSms())
			return true;
		return false;
	}

	@Test
	@DisplayName("Update perference successfully and response updated result")
	void updatePerferenceTest() {
		MarketingPerferenceDAO expect =  new MarketingPerferenceDAO("0001", "Mr./Ms. 0001","post pattern", "email pattern", "sms pattern", false);
		Mockito.when(repository.save(Mockito.any(MarketingPerferenceDAO.class))).thenReturn(expect);
		RequestPreference request = new RequestPreference(expect.getName(), expect.getPost(), expect.getEmail(), expect.getSms());
		ResponsePerference result = service.updatePerference(expect.getUserId(), request);
		assertNotNull(result);
		assertTrue(isEquals(expect, result));
	}

	@Test
	@DisplayName("Throw InvalidUpdateRequestException when request is invalid")
	void updatePerferenceWithInvalidReqTest() {
		RequestPreference request = new RequestPreference("", "", null, null);
		assertThrows(InvalidUpdateRequestException.class, () -> {service.updatePerference("0001", request);});
	}

	@Test
	@DisplayName("Get null when db update failed while updateing perference")
	void updatePerferenceWhenDatabaseFailed() {
		MarketingPerferenceDAO expect =  new MarketingPerferenceDAO("0001", "Mr./Ms. 0001","post pattern", "email pattern", "sms pattern", false);
		Mockito.when(repository.save(Mockito.any(MarketingPerferenceDAO.class))).thenReturn(null);
		ResponsePerference result = service.updatePerference(expect.getUserId(),
				new RequestPreference(expect.getName(), expect.getPost(), expect.getEmail(), expect.getSms()));
		assertNull(result);
	}
	
	@Test
	@DisplayName("Delete Perference and response deleted userId only")
	void deletePerferenceTest() {
		MarketingPerferenceDAO dataInDB =  new MarketingPerferenceDAO("0001", "Mr./Ms. 0001","post pattern", "email pattern", "sms pattern", false);
		Mockito.when(repository.findByUserId(Mockito.anyString())).thenReturn(dataInDB);
		MarketingPerferenceDAO deletedDAO =  new MarketingPerferenceDAO(dataInDB.getUserId(), dataInDB.getName(), dataInDB.getPost(), 
				dataInDB.getEmail(), dataInDB.getSms(), true);
		Mockito.when(repository.save(Mockito.any(MarketingPerferenceDAO.class))).thenReturn(deletedDAO);
		ResponsePerference result = service.deletePerference(dataInDB.getUserId());
		assertNotNull(result);
		assertNotNull(result.getUserId());
		assertFalse(isEquals(dataInDB, result));
	}

	@Test
	@DisplayName("Throw InvalidDeleteRequestException when userId format is invalid")
	void deletePerferenceTestWithInvalidUserId() {
		assertThrows(InvalidDeleteRequestException.class, () -> {service.deletePerference(" ");});
	}

	@Test
	@DisplayName("Throw UserIdNotExistException when userId is not exists in database")
	void deletePerferenceTestWithNotExistedUserId() {
		Mockito.when(repository.findByUserId(Mockito.anyString())).thenReturn(null);
		assertThrows(UserIdNotExistException.class, () -> {service.deletePerference("0002");});
	}

	@Test
	@DisplayName("Get null when db update failed while deleting perference")
	void deletePerferenceWhenDatabaseFailed() {
		MarketingPerferenceDAO dataInDB =  new MarketingPerferenceDAO("0001", "Mr./Ms. 0001","post pattern", "email pattern", "sms pattern", false);
		Mockito.when(repository.findByUserId(Mockito.anyString())).thenReturn(dataInDB);
		Mockito.when(repository.save(Mockito.any(MarketingPerferenceDAO.class))).thenReturn(null);
		ResponsePerference result = service.deletePerference(dataInDB.getUserId());
		assertNull(result);
	}
}
