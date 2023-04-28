package com.musalasoft.drone_delivery.model;

import com.musalasoft.drone_delivery.model.enums.Model;
import com.musalasoft.drone_delivery.model.enums.State;
import com.musalasoft.drone_delivery.service.dto.DroneDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
/**
 * @author Warren Zingwena
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Drone{

	@Id
	@Size(min =  1, max =  100)
	private String serialNumber;

	@Column (nullable = false)
	@Min(0)
	@Max(500)
	private Integer weight;

	@Column (nullable = false)
	@Min(0)
	@Max(100)
	private Integer capacity;

	@Enumerated(EnumType.STRING)
	private State state;

	@Enumerated(EnumType.STRING)
	private Model model;

	public Drone(String serialNumber, Integer weight, Integer capacity, State state, Model model) {
		this.serialNumber = serialNumber;
		this.weight = weight;
		this.capacity = capacity;
		this.state = state;
		this.model = model;
	}
}