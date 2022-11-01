package com.cloudleeaf.logisticsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import com.cloudleeaf.logisticsapi.model.AfterShipTrackingModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler(LogisticCustomException.class)
	public ResponseEntity<String> logisticBadRequestCustomExceptionHandler(Exception ex, WebRequest request) {
		log.error("Exception : " + ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> httpClientErrorExceptionHandler(Exception ex, WebRequest request)
			throws JsonMappingException, JsonProcessingException {
		log.error("Exception : " + ex);
		String detailedMsg = ex.getMessage();
		detailedMsg = detailedMsg.substring(detailedMsg.indexOf(":", 0) + 2);
		detailedMsg = detailedMsg.substring(1, detailedMsg.lastIndexOf("\""));
		ObjectMapper mapper = new ObjectMapper();
		AfterShipTrackingModel afterShipTrackingModel = mapper.readValue(detailedMsg, AfterShipTrackingModel.class);
		return new ResponseEntity<String>(afterShipTrackingModel.getMeta().getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<String> jsonProcessingExceptionHandler(Exception ex, WebRequest request) {
		log.error("Exception : " + ex);
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
