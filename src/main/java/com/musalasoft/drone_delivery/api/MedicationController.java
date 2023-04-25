package com.musalasoft.drone_delivery.api;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.services.MedicationDto;
import com.musalasoft.drone_delivery.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/medication")
@RequiredArgsConstructor
public class MedicationController {

	private final MedicationService medicationService;

	@PostMapping("/register")
	public ResponseEntity<Medication> registerMedication(@RequestBody @Valid MedicationDto medicationDto) {
		return ResponseEntity.ok(medicationService.registerMedication(medicationDto));
	}
}