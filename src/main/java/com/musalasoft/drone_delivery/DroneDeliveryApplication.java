package com.musalasoft.drone_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.musalasoft.drone_delivery.constants")
public class DroneDeliveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(DroneDeliveryApplication.class, args);
	}
}
