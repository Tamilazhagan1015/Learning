package com.cloudleeaf.logisticsapi.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tracking {

	private String id;
	private Date created_at;
	private Date updated_at;
	private Date last_updated_at;
	private String tracking_number;
	private String slug;
	private boolean active;
	private String[] android;
	private String custom_fields;
	private String customer_name;
	private int delivery_time;
	private String destination_country_iso3;
	private String courier_destination_country_iso3;
	private String[] emails;
	private String expected_delivery;
	private String[] ios;
	private String note;
	private String order_id;
	private String order_id_path;
	private Date order_date;
	private String origin_country_iso3;
	private String shipment_package_count;
	private Date shipment_pickup_date;
	private Date shipment_delivery_date;
	private String shipment_type;
	private String shipment_weight;
	private String shipment_weight_unit;
	private String signed_by;
	private String[] smses;
	private String source;
	private String tag;
	private String subtag;
	private String subtag_message;
	private String title;
	private String tracked_count;
	private String last_mile_tracking_supported;
	private String language;
	private String unique_token;
	private String[] checkpoints;
	private String[] subscribed_smses;
	private String[] subscribed_emails;
	private String return_to_sender;
	private Date order_promised_delivery_date;
	private String delivery_type;
	private String pickup_location;
	private String pickup_note;
	private String courier_tracking_link;
	private String first_attempted_at;
	private String courier_redirect_link;
	private String[] order_tags;
	private String order_number;
	private Date aftership_estimated_delivery_date;
	private String on_time_status;
	private String on_time_difference;
	private String tracking_account_number;
	private String tracking_origin_country;
	private String tracking_destination_country;
	private String tracking_key;
	private String tracking_postal_code;
	private Date tracking_ship_date;
	private String tracking_state;

}
