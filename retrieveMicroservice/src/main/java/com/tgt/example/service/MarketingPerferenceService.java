package com.tgt.example.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgt.example.dao.MarketingPerferenceDAO;
import com.tgt.example.exception.InvalidUserIdException;
import com.tgt.example.exception.UserIdNotExistException;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.repository.PerferenceRepository;

@Service
public class MarketingPerferenceService {
	private Logger logger = LoggerFactory.getLogger(MarketingPerferenceService.class);
	
	@Autowired private PerferenceRepository repository;

	public ResponsePerference getPerference(String userId) throws InvalidUserIdException, UserIdNotExistException {
		if(!isValidUpdatedRequest(userId)) throw new InvalidUserIdException();
		MarketingPerferenceDAO updateResult = repository.findByUserIdAndDeleted(userId, false);
		logger.debug("retrieve="+updateResult);
		if(updateResult ==null || StringUtils.isEmpty(updateResult.getUserId())) {
			logger.error("userId not exists:" + userId);
			throw new UserIdNotExistException();
		}
		return new ResponsePerference(updateResult.getUserId(), updateResult.getName(),
				updateResult.getPost(), updateResult.getEmail(), updateResult.getSms());
	}
	
	private boolean isValidUpdatedRequest(String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.error("userId is empty:"+userId);
			return false;
		}
		return true;
	}
}
