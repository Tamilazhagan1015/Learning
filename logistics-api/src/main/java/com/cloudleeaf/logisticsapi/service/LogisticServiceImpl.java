package com.cloudleeaf.logisticsapi.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloudleeaf.logisticsapi.configuration.LogisticsApiConfig;
import com.cloudleeaf.logisticsapi.exception.LogisticCustomException;
import com.cloudleeaf.logisticsapi.model.AfterShipTrackingModel;
import com.cloudleeaf.logisticsapi.model.DataDTO;
import com.cloudleeaf.logisticsapi.model.Shipment;
import com.cloudleeaf.logisticsapi.model.TrackingDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogisticServiceImpl implements LogisticService {

	@Autowired
	LogisticsApiConfig logisticsApiConfig;

	Map<String, Shipment> shipmentDetailsHmap = new HashMap<>();

	/**
	 * Aftership API EndPoints details
	 */
	private String AFTERSHIP_TRACKING_URL = "https://api.aftership.com/v4/trackings";
	private String AFTERSHIP_GET_TRACKING_URL = "https://api.aftership.com/v4/last_checkpoint";

	private List<String> courierCodeList = (List<String>) Arrays.asList("FedEx,UPS,USPS".split(","));
	private JSONObject courierCodeKeySlugValueJson = new JSONObject(
			"{\"FedEx\":\"fedex\",\"UPS\":\"ups\",\"USPS\":\"usps\"}");

	/**
	 * Description : Posting the Shipment Details in the AfterShip Using
	 * RestTemplate.
	 * 
	 * @param shipment
	 * @return
	 * @throws JsonProcessingException
	 */
	@Override
	public Shipment createShipmentService(Shipment shipment) throws JsonProcessingException {

		if (StringUtils.isEmpty(shipment.getTrackingNumber())) {
			log.error("Tracking Number should not be Null or Empty");
			throw new LogisticCustomException("Tracking Number should not be Null or Empty");
		}

		if (StringUtils.isEmpty(shipment.getOrigin())) {
			log.error("Origin should not be Null or Empty");
			throw new LogisticCustomException("Origin should not be Null or Empty");
		}
		if (StringUtils.isEmpty(shipment.getDestination())) {
			log.error("Destination should not be Null or Empty");
			throw new LogisticCustomException("Destination should not be Null or Empty");
		}

		if (StringUtils.isEmpty(shipment.getCourierCode())) {
			log.error("Courier Code should not be Null or Empty");
			throw new LogisticCustomException("Courier Code should not be Null or Empty");
		} else if (!courierCodeList.contains(shipment.getCourierCode())) {
			log.error("Courier Code is Wrong. It should be FedEx, USPS or UPS");
			throw new LogisticCustomException("Courier Code is Wrong. It should be FedEx, USPS or UPS");
		}

		ObjectMapper mapper = new ObjectMapper();

		log.info("Shipment Details : " + mapper.writeValueAsString(shipment));

		shipmentDetailsHmap.put(shipment.getTrackingNumber(), shipment);

		TrackingDTO trackingDTO = new TrackingDTO();
		trackingDTO.setTracking_number(shipment.getTrackingNumber());
		trackingDTO.setSlug(courierCodeKeySlugValueJson.getString(shipment.getCourierCode()));

		DataDTO dataDTO = new DataDTO();
		dataDTO.setTracking(trackingDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("aftership-api-key", logisticsApiConfig.getAfterShipApiKey());

		HttpEntity<Object> httpEntity = new HttpEntity<>(dataDTO, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> afterShipCreateTrackingRes = restTemplate.exchange(AFTERSHIP_TRACKING_URL,
				HttpMethod.POST, httpEntity, String.class);

		AfterShipTrackingModel afterShipTrackingModel = mapper.readValue(afterShipCreateTrackingRes.getBody(),
				AfterShipTrackingModel.class);

		shipment.setCurrentStatus(afterShipTrackingModel.getData().getTracking().getTag());
		shipment.setId(afterShipTrackingModel.getData().getTracking().getId());

		return shipment;
	}

	/**
	 * Description : Fetching the shipment status using trackingNumber and the
	 * courier code
	 * 
	 * @param trackingNumber
	 * @param courierCode
	 * @return
	 */
	@Override
	public Shipment fetchShipmentStatusService(String trackingNumber) throws JsonProcessingException {

		Shipment shipment = null;

		if (StringUtils.isEmpty(trackingNumber)) {
			log.error("Tracking Number should not be Null or Empty");
			throw new LogisticCustomException("Tracking Number should not be Null or Empty");
		}

		ObjectMapper mapper = new ObjectMapper();

		if (shipmentDetailsHmap.containsKey(trackingNumber)) {
			shipment = shipmentDetailsHmap.get(trackingNumber);
		} else {
			throw new LogisticCustomException(trackingNumber + " is not found our Logistic Application.");
		}

		log.info("Shipment Details : " + mapper.writeValueAsString(shipment));

		HttpHeaders headers = new HttpHeaders();
		headers.set("aftership-api-key", logisticsApiConfig.getAfterShipApiKey());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		String url = AFTERSHIP_GET_TRACKING_URL + "/" + courierCodeKeySlugValueJson.getString(shipment.getCourierCode())
				+ "/" + trackingNumber;

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> getForObject = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

		AfterShipTrackingModel afterShipTrackingModel = mapper.readValue(getForObject.getBody(),
				AfterShipTrackingModel.class);

		shipment.setCurrentStatus(afterShipTrackingModel.getData().getTag());
		shipment.setId(afterShipTrackingModel.getData().getId());

		return shipment;
	}

}
