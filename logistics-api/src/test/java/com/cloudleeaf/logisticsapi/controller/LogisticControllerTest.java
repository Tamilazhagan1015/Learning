package com.cloudleeaf.logisticsapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cloudleeaf.logisticsapi.model.Shipment;
import com.cloudleeaf.logisticsapi.service.LogisticService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@WebMvcTest
public class LogisticControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LogisticService logisticService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenShipmentObject_RegisterShipment_ReturnAfterShipModel() throws Exception {
		Shipment shipment = new Shipment();
		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
		shipment.setTrackingNumber("1Z81V2780448192887");
		shipment.setCourierCode("UPS");

		ResultActions response = mockMvc.perform(post("/api/logistic/shipment")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(shipment)));

		response.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void givenTrackingAndCourierCode_FetchShipmentStatus_ReturnAfterShipModel() throws Exception {
		String trackingNumber = "1Z81V2780448192887";
		String courierCode = "UPS";

		ResultActions response = mockMvc
				.perform(get("/api/logistic/shipment/" + trackingNumber));

		response.andExpect(status().isOk()).andDo(print());
	}
}
