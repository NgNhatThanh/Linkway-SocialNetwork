package com.social_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.FileInputStream;

@SpringBootApplication
public class SocialNetworkApplication {

	public static void main(String[] args) {

//		File f = new File("dsfdffsd");
//
//		FileInputStream fis = new FileInputStream(f);

		SpringApplication.run(SocialNetworkApplication.class, args);
	}

}
