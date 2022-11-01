package com.cloudleeaf.logisticsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDTO {
	
	private String tracking_number;
	private String slug;
	
}
