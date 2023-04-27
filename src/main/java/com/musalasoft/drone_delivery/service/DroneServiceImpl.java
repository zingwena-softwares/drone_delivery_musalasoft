package com.musalasoft.drone_delivery.service;
import com.musalasoft.drone_delivery.exception.ClientException;
import com.musalasoft.drone_delivery.exception.ExceptionMessageCreator;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.model.enums.State;
import com.musalasoft.drone_delivery.repository.DroneRepo;
import com.musalasoft.drone_delivery.repository.MedicationRepo;
import com.musalasoft.drone_delivery.service.dto.DroneDto;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class DroneServiceImpl implements DroneService {

	private final DroneRepo droneRepo;
	private final MedicationRepo medicationRepo;
	private final ExceptionMessageCreator messageCreator;
	private final ModelMapper modelMapper;

	static final Logger LOGGER = Logger.getLogger(DroneServiceImpl.class.getName());

	@Override
	public List<Drone>  getAllDrones() {
		return droneRepo.findAll();
	};

	@Override
	public Integer getCapacityBySerial(String serial) {
		return droneRepo.getCapacityBySerial(serial);
	}

	@Override
	public List<Drone> getDroneByState(State state){
		return droneRepo.findByState(state);
	};

	/*
	* IDLE -> LOADING -> LOADED
	*
	* increase WEIGHT
	* */
	@Override
	public  String  loadDroneWithMedications(String droneSerial, List<String> medicationCodes) {
		Drone d = droneRepo.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)));
		medicationCodes.forEach(mC -> {
			Medication m = medicationRepo.findById(mC).orElseThrow(() -> ClientException.of(messageCreator.createMessage(MEDICATION_CODE_NOT_FOUND)));
			m.setDrone(d);
			medicationRepo.saveAndFlush(m);
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
		droneRepo.saveAndFlush(d);
		return "Successful";
	}

	@Override
	public List<Medication> getDroneMedications(String droneSerial) {
		Drone d = droneRepo.findById(droneSerial).orElseThrow(() -> ClientException.of(messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)));
		return medicationRepo.findByDrone(d);
	}

	@Override
	public Drone registerDrone(DroneDto droneDto) {
		if (droneRepo.count() == DRONE_FLEET_LIMIT)
			ClientException.of(messageCreator.createMessage(DRONE_FLEET_SIZE_EXCEEDED));
		Drone drone = modelMapper.map(droneDto, Drone.class);
		drone.setWeight(0);
		drone.setState(State.IDLE);
		return droneRepo.saveAndFlush(drone);
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