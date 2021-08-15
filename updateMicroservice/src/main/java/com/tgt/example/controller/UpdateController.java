package com.tgt.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.example.httpentity.RequestPreference;
import com.tgt.example.httpentity.ResponsePerference;
import com.tgt.example.service.MarketingPerferenceService;

@RestController
@RequestMapping (value="/v1/api")
public class UpdateController {
	
	@Autowired private MarketingPerferenceService perferenceService;

	@GetMapping("/heartbeat")
    public ResponseEntity<HttpStatus> heartBeat() {
		return ResponseEntity.ok(HttpStatus.OK);
    }
	
	@PostMapping(path="/perferences/userId/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponsePerference> updatePerference(@PathVariable("userId") String userId, @RequestBody RequestPreference requestBody) {
		ResponsePerference result = perferenceService.updatePerference(userId, requestBody);
		if(result!=null) return new ResponseEntity<ResponsePerference>(result, HttpStatus.OK);
		else return new ResponseEntity<ResponsePerference>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(path="/perferences/userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponsePerference> deletePerference(@PathVariable("userId") String userId) {
		ResponsePerference result = perferenceService.deletePerference(userId);
		if(result!= null) return new ResponseEntity<ResponsePerference>(result, HttpStatus.OK);
		else return new ResponseEntity<ResponsePerference>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
