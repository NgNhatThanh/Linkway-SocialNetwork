package com.social_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.FileInputStream;

@SpringBootApplication
public class SocialNetworkApplication {

	public static void main(String[] args) {

		SpringApplication.run(SocialNetworkApplication.class, args);
	}

}
