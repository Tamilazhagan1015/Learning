package com.cloudleeaf.logisticsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudleeaf.logisticsapi.model.Shipment;
import com.cloudleeaf.logisticsapi.service.LogisticService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

/**
 * Description : Logistics Controller for registering and tracking the Shipment
 * of Cloudleaf
 * 
 * @author t.gunasekar
 *
 */
@RestController
@RequestMapping(value = "/api/logistic")
@Slf4j
public class LogisticController {

	@Autowired
	LogisticService logisticService;

	/**
	 * Description : API for registering the Shipment details in AfterShip
	 * 
	 * @param shipment
	 * @return
	 * @throws JsonProcessingException
	 */
	@PostMapping(value = "/shipment")
	public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) throws JsonProcessingException {
		log.info("Entered into Shipment Register API");
		Shipment shipmentDetails = logisticService.createShipmentService(shipment);
		log.info("Done with Shipment Register API");
		return new ResponseEntity<>(shipmentDetails, HttpStatus.OK);
	}

	/**
	 * Description : API for fetching status of the Shipment from AfterShip
	 * 
	 * @param trackingNumber
	 * @param courierCode
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping(value = "/shipment/{trackingNumber}")
	public ResponseEntity<Shipment> fetchShipmentStatus(@PathVariable("trackingNumber") String trackingNumber)
			throws JsonProcessingException {
		log.info("Entered into Shipment Status API");
		Shipment shipmentDetails = logisticService.fetchShipmentStatusService(trackingNumber);
		log.info("Done with Shipment Status API");
		return new ResponseEntity<>(shipmentDetails, HttpStatus.OK);
	}
}
