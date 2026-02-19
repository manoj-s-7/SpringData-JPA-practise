package com.manojs.hospitalmanagement;

import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import com.manojs.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
@SpringBootTest

public class PatientTests {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;


    @Test
    public void testPatientRepository() {
        List<Patient> all = patientRepository.findAll();
        System.out.println(all);
    }

    @Test
    void getByName() {
        Patient patientRepositoryByName = patientRepository.findByName("Arjun").orElse(null);
        System.out.println(patientRepositoryByName);

    }

}