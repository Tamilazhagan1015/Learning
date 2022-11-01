package com.cloudleeaf.logisticsapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.cloudleeaf.logisticsapi.configuration.LogisticsApiConfig;
import com.cloudleeaf.logisticsapi.exception.LogisticCustomException;
import com.cloudleeaf.logisticsapi.model.Shipment;

@SpringBootTest(classes = LogisticService.class)
public class LogisticServiceTest {

	@Mock
	LogisticsApiConfig logisticsApiConfig;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private LogisticServiceImpl logisticServiceImpl;

	private Shipment shipment;

	private String AFTERSHIP_TRACKING_URL = "https://api.aftership.com/v4/trackings";
	private String AFTERSHIP_GET_TRACKING_URL = "https://api.aftership.com/v4/last_checkpoint";

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void givenShipmentObjectWithOriginNull_Empty_whenResgisterShipment_thenThrowError() {
		shipment = new Shipment();
		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
		shipment.setTrackingNumber("1Z81V2780448192887");
		shipment.setCourierCode("UPS");

		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));

		shipment.setOrigin("");
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));
	}

	@Test
	public void givenShipmentObjectWithDestinationNull_Empty_whenResgisterShipment_thenThrowError() {
		shipment = new Shipment();
		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
		shipment.setTrackingNumber("1Z81V2780448192887");
		shipment.setCourierCode("UPS");

		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));

		shipment.setDestination("");
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));
	}

	@Test
	public void givenShipmentObjectWithTrackingNull_Empty_whenResgisterShipment_thenThrowError() {
		shipment = new Shipment();
		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
		shipment.setCourierCode("UPS");

		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));

		shipment.setTrackingNumber("");
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));
	}

	@Test
	public void givenShipmentObjectWithCourierCodeNull_Empty_whenResgisterShipment_thenThrowError() {
		shipment = new Shipment();
		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
		shipment.setTrackingNumber("1Z81V2780448192887");

		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));

		shipment.setCourierCode("");
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));
	}

	@Test
	public void givenShipmentObjectWithWrongCourierCode_whenResgisterShipment_thenThrowError() {
		shipment = new Shipment();
		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
		shipment.setTrackingNumber("1Z81V2780448192887");
		shipment.setCourierCode("ABC");

		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));

		shipment.setCourierCode("");
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.createShipmentService(shipment));
	}

	@Test
	public void givenTrackingNumber_whenTrackingShipmentStatus_thenThrowError() {
		String trackingNumber = "1Z81V2780448192887";
		assertThrows(LogisticCustomException.class,
				() -> logisticServiceImpl.fetchShipmentStatusService(trackingNumber));
		assertThrows(LogisticCustomException.class,
				() -> logisticServiceImpl.fetchShipmentStatusService(trackingNumber));
	}

	@Test
	public void givenTrackingNumberNull_Empty_whenTrackingShipmentStatus_thenThrowError() {
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.fetchShipmentStatusService(null));
		assertThrows(LogisticCustomException.class, () -> logisticServiceImpl.fetchShipmentStatusService(""));
	}

//	@Test
//	public void givenShipmentObject_whenResgisterShipment_thenReturnShipmentObject() throws JsonProcessingException {
//		shipment = new Shipment();
//		shipment.setOrigin("990 S Hwy 395 S, Hermiston, OR 97838");
//		shipment.setDestination("3505 Factoria Blvd SE, Bellevue, WA 98006");
//		shipment.setTrackingNumber("1Z81V2780448192887");
//		shipment.setCourierCode("UPS");
//		
//		Shipment resgisteredShipment = logisticServiceImpl.createShipmentService(shipment);
//		assertNotNull(resgisteredShipment);
//	}

}
