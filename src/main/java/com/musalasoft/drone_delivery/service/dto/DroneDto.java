package com.musalasoft.drone_delivery.service.dto;

import com.musalasoft.drone_delivery.model.enums.Model;
import com.musalasoft.drone_delivery.model.enums.State;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
public class DroneDto {

	@Size(min =  1, max =  100)
	private String serialNumber;

	@Min(0)
	@Max(500)
	private Integer weight;

	@Min(0)
	@Max(100)
	private Integer capacity;

	@Enumerated(EnumType.STRING)
	private State state;

	@Enumerated(EnumType.STRING)
	private Model model;
}