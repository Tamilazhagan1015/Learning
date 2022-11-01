package com.cloudleeaf.logisticsapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties
@Getter
public class LogisticsApiConfig {

	@Value("${aftership.api.key}")
	private String afterShipApiKey;

}
