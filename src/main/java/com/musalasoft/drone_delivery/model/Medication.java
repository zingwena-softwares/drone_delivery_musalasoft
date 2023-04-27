package com.musalasoft.drone_delivery.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Medication {

	@Id
	@Pattern(
		regexp = "[A-Z0-9_]+",
		message = "only upper case letters, underscore and numbers allowed"
	)
	private String code;

	@Column(nullable = false)
	@Pattern(
		regexp = "[a-zA-Z_0-9-]+",
		message = "only letters, numbers, underscore and hyphen allowed"
	)
	private String name;

	@Column
	private String picture;

	@Column (nullable = false)
	private Integer weight;

	@ManyToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_drone")
	private Drone drone;

	public Medication(String code, String name, String picture, Integer weight, Drone drone) {
		this.code = code;
		this.name = name;
		this.picture = picture;
		this.weight = weight;
		this.drone = drone;
	}


}