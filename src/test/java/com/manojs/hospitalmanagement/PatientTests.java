package com.manojs.hospitalmanagement;

import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

public class PatientTests {
    @Autowired
    private PatientRepository patientRepository;


    @Test
    public void testPatientRepository(){
        List<Patient> all = patientRepository.findAll();
        System.out.println(all);
    }
}
