package com.musalasoft.drone_delivery.api;

import com.musalasoft.drone_delivery.exception.ExceptionMessageCreator;
import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.repository.DroneRepo;
import com.musalasoft.drone_delivery.repository.MedicationRepo;
import com.musalasoft.drone_delivery.service.DroneService;
import com.musalasoft.drone_delivery.service.dto.DroneDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.musalasoft.drone_delivery.model.enums.Model.LIGHTWEIGHT;
import static com.musalasoft.drone_delivery.model.enums.State.IDLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
/**
 * @author Warren Zingwena
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DroneControllerTest {
    @Autowired
    private DroneService droneService;
    @MockBean
    private DroneRepo droneRepo;

    @MockBean
    private MedicationRepo mRepository;
    @MockBean
    private ExceptionMessageCreator messageCreator;

    @Test
    void getAllDrone() {
    }

    @Test
    void getDroneByState() {
        when(droneRepo.findByState(IDLE)).thenReturn(Stream
                .of(new Drone("124yupop45", 100, 20, IDLE,LIGHTWEIGHT)).collect(Collectors.toList()));
        assertEquals(1, droneService.getDroneByState(IDLE).size());
    }

    @Test
    void getAvailableDrones() {
        when(droneRepo.findAll()).thenReturn(Stream.of(
                new Drone("124yupop", 100, 20, IDLE,LIGHTWEIGHT),
                new Drone("124yupop45", 100, 20, IDLE,LIGHTWEIGHT)).collect(Collectors.toList()));
        assertEquals(2, droneService.getAllDrones().size());
    }

    @Test
    void getCapacityBySerial() {
        when(droneRepo.getCapacityBySerial("124yupop45")).thenReturn(100);
        assertEquals(100, droneService.getCapacityBySerial("124yupop45"));
    }
    @Test
    void getDroneMedications() {
        Drone drone =  new Drone("dronchik_45", 100, 20, IDLE,LIGHTWEIGHT);
        Optional<Drone> optionalDrone = droneRepo.findById(drone.getSerialNumber());
        List<Medication> list = new ArrayList<Medication>();
        Medication medication1 = new  Medication("111189943", "paracetamol1", "string", 12, drone);
        Medication medication2 = new  Medication("111182222", "paracetamol2", "string", 12, drone);
        Medication medication3 = new  Medication("222209733", "paracetamol3", "string", 12, drone);
        list.add(medication1);
        list.add(medication2);
        list.add(medication3);
        when(optionalDrone).thenReturn(Optional.of(drone));
        assertNotEquals(list, mRepository.findByDrone(drone));
    }

    @Test
    void registerDrone() {
        Drone drone =  new Drone("dronchik_45", 100, 20, IDLE,LIGHTWEIGHT);
        DroneDto droneDto =  new DroneDto("dronchik_45", 100, 20, IDLE,LIGHTWEIGHT);
        when(droneRepo.save(drone)).thenReturn(drone);
        droneService.registerDrone(droneDto);
    }
    @Test
   void  loadDroneWithMedication(){
        Drone drone =  new Drone("dronchik_45", 100, 20, IDLE,LIGHTWEIGHT);
        Medication medication =  new  Medication("222209733", "paracetamol3", "string", 12, drone);
        Optional<Medication> optionalMedication = mRepository.findById(medication.getCode());
        when(optionalMedication).thenReturn(Optional.of(medication));
        assertNotEquals(medication, mRepository.saveAndFlush(medication));

    }

}