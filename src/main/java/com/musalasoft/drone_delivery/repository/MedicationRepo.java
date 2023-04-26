package com.musalasoft.drone_delivery.repository;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepo extends JpaRepository<Medication, String> {
	List<Medication>  findByDrone(Drone drone);
}
