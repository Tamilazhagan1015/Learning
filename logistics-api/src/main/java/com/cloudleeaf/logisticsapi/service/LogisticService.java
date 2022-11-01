package com.cloudleeaf.logisticsapi.service;

import com.cloudleeaf.logisticsapi.model.Shipment;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface LogisticService {

	public Shipment createShipmentService(Shipment shipment) throws JsonProcessingException;

	public Shipment fetchShipmentStatusService(String trackingNumber) throws JsonProcessingException;
}
