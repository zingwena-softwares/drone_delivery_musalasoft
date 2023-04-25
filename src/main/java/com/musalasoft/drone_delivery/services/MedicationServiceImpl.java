package com.musalasoft.drone_delivery.services;
import com.musalasoft.drone_delivery.dao.MedicationDao;
import com.musalasoft.drone_delivery.model.Medication;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationServiceImpl  implements MedicationService{

	private final MedicationDao mR;
	private final ModelMapper modelMapper;

	@Override
	public Medication registerMedication(MedicationDto medicationDto) {
		Medication medication = modelMapper.map(medicationDto, Medication.class);
		return mR.saveAndFlush(medication);
	}
}