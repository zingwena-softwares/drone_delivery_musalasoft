package com.musalasoft.drone_delivery.services;

import com.musala.dronedispatchcontroller.domain.Medication;
import com.musala.dronedispatchcontroller.service.dto.MedicationDto;

public interface MedicationService {

	Medication registerMedication(MedicationDto medicationDto);
}