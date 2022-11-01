package com.cloudleeaf.logisticsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
	
	private String id;
	private String origin;
	private String destination;
	private String currentStatus;
	private String trackingNumber;
	private String courierCode;
}
