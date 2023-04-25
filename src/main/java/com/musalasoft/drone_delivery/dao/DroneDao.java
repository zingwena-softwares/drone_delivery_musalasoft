package com.musalasoft.drone_delivery.dao;

import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DroneDao extends JpaRepository<Drone, String> {

	List<Drone> findByState(State state);

	@Query("SELECT  d.capacity FROM Drone d WHERE d.serialNumber = :serial")
	Integer getCapacityForSerial(@Param("serial") String serial);
}