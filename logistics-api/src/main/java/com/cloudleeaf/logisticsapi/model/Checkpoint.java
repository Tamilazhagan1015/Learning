package com.cloudleeaf.logisticsapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Checkpoint {

	private String slug;
	private String created_at;
	private String checkpoint_time;
	private String city;
	private String[] coordinates;
	private String coordinate;
	private String country_iso3;
	private String country_name;
	private String message;
	private String state;
	private String tag;
	private String subtag;
	private String subtag_message;
	private String zip;

}
