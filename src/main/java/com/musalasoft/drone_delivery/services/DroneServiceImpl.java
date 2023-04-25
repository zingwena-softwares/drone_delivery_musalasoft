package com.musalasoft.drone_delivery.services;
import com.musalasoft.drone_delivery.dao.DroneDao;
import com.musalasoft.drone_delivery.dao.MedicationDao;
import com.musalasoft.drone_delivery.exception.ClientException;
import com.musalasoft.drone_delivery.exception.ExceptionMessageCreator;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.model.enums.State;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import static com.musalasoft.drone_delivery.constants.ServiceConstants.*;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

	private final DroneDao dR;
	private final MedicationDao mR;
	private final ExceptionMessageCreator messageCreator;
	private final ModelMapper modelMapper;



	static final Logger LOGGER = Logger.getLogger(DroneServiceImpl.class.getName());

	@Override
	public List<Drone> getAllDrones() {
		return dR.findAll();
	};

	@Override
	public Integer getCapacityForSerial(String serial) {
		return dR.getCapacityForSerial(serial);
	}

	@Override
	public List<Drone> getDroneByState(State state){
		return dR.findByState(state);
	};

	/*
	* IDLE -> LOADING -> LOADED
	*
	* increase WEIGHT
	* */
	@Override
	public  String  loadDroneWithMedications(String droneSerial, List<String> medicationCodes) {
		Drone d = dR.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)));
		medicationCodes.forEach(mC -> {
			Medication m = mR.findById(mC).orElseThrow(() -> ClientException.of(messageCreator.createMessage(MEDICATION_CODE_NOT_FOUND)));
			m.setDrone(d);
			mR.saveAndFlush(m);
			int newWeight = d.getWeight() + m.getWeight();
			if (newWeight < WEIGHT_LIMIT) {
				d.setWeight(newWeight);
				d.setState(State.LOADING);
			}
			if (newWeight == WEIGHT_LIMIT) {
				d.setWeight(newWeight);
				d.setState(State.LOADED);
			}
			if (newWeight > WEIGHT_LIMIT)
				// we don't set status as LOADED as we might try to load a lighter medication
				ClientException.of(messageCreator.createMessage(MEDICATION_OVERLOAD)) ;
		});
		dR.saveAndFlush(d);
		return messageCreator.createMessage(DRONE_LOADED);
	}

	@Override
	public List<Medication> getDroneMedications(String droneSerial) {
		Drone d = dR.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)));
		return mR.findByDrone(d);
	}

	@Override
	public Drone registerDrone(DroneDto droneDto) {
		if (dR.count() == DRONE_FLEET_LIMIT)
			ClientException.of(messageCreator.createMessage(DRONE_FLEET_SIZE_EXCEEDED));
		Drone drone = modelMapper.map(droneDto, Drone.class);
		drone.setWeight(0);
		drone.setState(State.IDLE);
		return dR.saveAndFlush(drone);
	}

	@Scheduled(fixedRateString = "${scheduler.interval}")
	@Async
	public void logCapacity() {
		log.info("Drone capacity check at "+ formatEventDate());
		List<Drone>  drones = getAllDrones();
		drones.forEach(d -> {
			log.info("serial number - {}, capacity - {}", d.getSerialNumber(), d.getCapacity());
		});
	}

	private  String formatEventDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
		return formatter.format(LocalDateTime.now());
	}
}