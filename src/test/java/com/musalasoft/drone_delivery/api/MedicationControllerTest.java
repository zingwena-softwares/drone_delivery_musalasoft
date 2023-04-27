package com.musalasoft.drone_delivery.api;

import com.musalasoft.drone_delivery.model.Drone;
import com.musalasoft.drone_delivery.model.Medication;
import com.musalasoft.drone_delivery.repository.MedicationRepo;
import com.musalasoft.drone_delivery.service.MedicationService;
import com.musalasoft.drone_delivery.service.dto.DroneDto;
import com.musalasoft.drone_delivery.service.dto.MedicationDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.musalasoft.drone_delivery.model.enums.Model.LIGHTWEIGHT;
import static com.musalasoft.drone_delivery.model.enums.State.IDLE;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
class MedicationControllerTest {
    @Autowired
    private MedicationService medicationService;
    @MockBean
    private MedicationRepo mRepository;
    @Test
    void registerMedication() {
        Drone drone =  new Drone("dronchik_45", 100, 20, IDLE,LIGHTWEIGHT);
        Medication medication = new  Medication("111189943", "paracetamol1", "string", 12, drone);
        MedicationDto medicationDto = new  MedicationDto("111189943", "paracetamol1", "string", 12, drone);
        when(mRepository.save(medication)).thenReturn(medication);
        medicationService.registerMedication(medicationDto);
    }
}