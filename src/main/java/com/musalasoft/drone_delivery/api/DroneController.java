package com.musalasoft.drone_delivery.api;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.model.enums.State;
import com.musalasoft.drone_delivery.service.DroneService;
import com.musalasoft.drone_delivery.service.dto.DroneDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("api/drone")
public class DroneController {
	private final DroneService droneService;
	@GetMapping
	public ResponseEntity<List<Drone>> getAllDrone() {
		return ResponseEntity.ok().body(droneService.getAllDrones());
	}

	@GetMapping("/getbystate/{state}")
	public ResponseEntity<List<Drone>> getDroneByState(@PathVariable State state) {
		return ResponseEntity.ok().body(droneService.getDroneByState(state));
	}
	@GetMapping("/getavailable")
	public ResponseEntity<List<Drone>> getAvailableDrones() {
		List<Drone> result = new ArrayList<>();
		result.addAll(droneService.getDroneByState(State.IDLE));
		result.addAll(droneService.getDroneByState(State.LOADING));
		result = result.stream()
				.filter(drone -> drone.getCapacity() >= 25)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}
	@GetMapping("/getcapacitybyserial/{serial}")
	public ResponseEntity<Integer> getCapacityForSerial(@PathVariable String serial) {
		return ResponseEntity.ok().body(droneService.getCapacityBySerial(serial));
	}
	@GetMapping("/getmedications/{serial}")
	public ResponseEntity<List<Medication>> getDroneMedications(@PathVariable String serial) {
		return ResponseEntity.ok().body(droneService.getDroneMedications(serial));
	}
	@PostMapping("/register")
	public ResponseEntity<Drone> registerDrone(@RequestBody @Valid DroneDto droneDto) {
		return ResponseEntity.ok(droneService.registerDrone(droneDto));
	}
	@PutMapping("/loaddrone")
	public ResponseEntity<String> loadDroneWithMedication(@RequestParam  String droneSerial, @RequestParam List<String> medicationCodes) {
		return ResponseEntity.ok().body(droneService.loadDroneWithMedications(droneSerial, medicationCodes));
	}
}