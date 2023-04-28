package com.musalasoft.drone_delivery.service.dto;

import com.musalasoft.drone_delivery.model.Drone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
/**
 * @author Warren Zingwena
 */
@Getter
@NoArgsConstructor
public class MedicationDto {

	@Pattern(
		regexp = "[A-Z0-9_]+",
		message = "only upper case letters, underscore and numbers allowed"
	)
	private String code;

	@Pattern(
		regexp = "[a-zA-Z_0-9-]+",
		message = "only letters, numbers, underscore and hyphen allowed"
	)
	private String name;

	private String picture;

	@Positive
	private Integer weight;

	private Drone drone;

	public MedicationDto(String code, String name, String picture, Integer weight, Drone drone) {
		this.code = code;
		this.name = name;
		this.picture = picture;
		this.weight = weight;
		this.drone = drone;
	}
}