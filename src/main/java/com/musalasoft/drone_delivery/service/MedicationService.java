package com.musalasoft.drone_delivery.service;


import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.service.dto.MedicationDto;
/**
 * @author Warren Zingwena
 */
public interface MedicationService {

	Medication registerMedication(MedicationDto medicationDto);
}