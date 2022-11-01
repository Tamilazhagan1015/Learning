package com.cloudleeaf.logisticsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
	
	private Tracking tracking;
	private String id;
	private String tracking_number;
	private String slug;
	private String tag;
	private String subtag;
	private String subtag_message;
	private Checkpoint checkpoint;

}
