package com.tgt.example.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgt.example.dao.MarketingPerferenceDAO;
import com.tgt.example.exception.InvalidDeleteRequestException;
import com.tgt.example.exception.InvalidUpdateRequestException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.RequestPreference;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.repository.PerferenceRepository;

@Service
public class MarketingPerferenceService {
	private Logger logger = LoggerFactory.getLogger(MarketingPerferenceService.class);
	
	@Autowired private PerferenceRepository repository;

	public ResponsePerference updatePerference(String userId, RequestPreference request) throws InvalidUpdateRequestException {
		if(!isValidUpdatedRequest(userId, request)) throw new InvalidUpdateRequestException();
		MarketingPerferenceDAO perference =  new MarketingPerferenceDAO(userId, request.getName(),
				request.getPost(), request.getEmail(), request.getSms(), false);
		MarketingPerferenceDAO updateResult = repository.save(perference);
		logger.debug("update="+updateResult);
		if(updateResult ==null || StringUtils.isEmpty(updateResult.getUserId())) {
			logger.error("update failed: " + request);
			return null;
		}
		return new ResponsePerference(updateResult.getUserId(), updateResult.getName(),
				updateResult.getPost(), updateResult.getEmail(), updateResult.getSms());
	}
	
	private boolean isValidUpdatedRequest(String userId, RequestPreference req) {
		if (StringUtils.isBlank(userId)) {
			logger.error("userId is empty:"+userId);
			return false;
		} else if(req != null && StringUtils.isBlank(req.getPost()) && StringUtils.isBlank(req.getEmail())
				&& StringUtils.isBlank(req.getSms())) {
			logger.error("Invalid request:{post=" + req.getPost() + ",email=" + req.getEmail() + ",sms=" + req.getSms() +"}");
			return false;
		}
		return true;
	}

	public ResponsePerference deletePerference(String userId) throws InvalidUpdateRequestException, UserIdNotExistException {
		if(!isValidUpdatedRequest(userId, null)) throw new InvalidDeleteRequestException();
		MarketingPerferenceDAO perference = repository.findByUserId(userId);
		if(perference == null || StringUtils.isEmpty(perference.getUserId())) {
			logger.error("could not find userId: " + userId);
			throw new UserIdNotExistException();
		}
		perference.setDeleted(true);
		MarketingPerferenceDAO updateResult = repository.save(perference);
		if(updateResult == null) {
			logger.error("Update fail:" + perference);
			return null;
		}
		return new ResponsePerference(updateResult.getUserId());
	}
}
