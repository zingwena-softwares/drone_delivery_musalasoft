package com.musalasoft.drone_delivery.services;


import com.musalasoft.drone_delivery.model.Medication;

public interface MedicationService {

	Medication registerMedication(MedicationDto medicationDto);
}