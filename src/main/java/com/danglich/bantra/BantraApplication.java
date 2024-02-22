package com.danglich.bantra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.danglich.bantra.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BantraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BantraApplication.class, args);
	}

}
