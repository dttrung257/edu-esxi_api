package com.uet.esxi_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "db")
@Getter
@Setter
public class DatabaseConfig {
	private String name;
	private String username;
	private String password;
}
