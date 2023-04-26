package com.musalasoft.drone_delivery.service;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.model.enums.State;
import com.musalasoft.drone_delivery.service.dto.DroneDto;

import java.util.List;

public interface DroneService {

	List<Drone>  getAllDrones();

	Integer getCapacityForSerial(String serialNumber);

	List<Drone>  getDroneByState(State state);

	String  loadDroneWithMedications(String droneSerial, List<String> medicationCodes);

	List<Medication> getDroneMedications(String droneSerial);

	Drone registerDrone(DroneDto droneDto);

	void logCapacity() ;
}