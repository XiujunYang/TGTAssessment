package com.tgt.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tgt.example.dao.MarketingPerferenceDAO;

@Repository
public interface PerferenceRepository extends CrudRepository<MarketingPerferenceDAO, String> {
	MarketingPerferenceDAO findByUserId(String userId);
}