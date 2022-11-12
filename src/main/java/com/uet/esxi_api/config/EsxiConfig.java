package com.uet.esxi_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "esxi_server")
@Getter
@Setter
public class EsxiConfig {
	private String ip;
	private String username;
	private String password;
}
