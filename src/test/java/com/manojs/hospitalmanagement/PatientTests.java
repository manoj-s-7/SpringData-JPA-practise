package com.manojs.hospitalmanagement;

import com.manojs.hospitalmanagement.dto.PatientContactDTO;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import com.manojs.hospitalmanagement.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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
    @Test
    void JPQLTest(){
//        List<Patient> aPositive = patientRepository.findByBloodGroup(BloodGroupType.A_Positive);
//        System.out.println(aPositive);
//
//        List<BloodGroupCountDTO> patients = patientRepository.groupByBloodGroup();
//        System.out.println(patients);

//        List<Patient> patientByBornAfterDate = patientRepository.findPatientByBornAfterDate(LocalDate.of(2003, 3, 14));
//        System.out.println(patientByBornAfterDate);

//        List<Patient> aLlMalePatients = patientRepository.findALlMalePatients();
//        System.out.println(aLlMalePatients);

//        int rowCount = patientRepository.updateNameByID("Manthan KU", 1L);
//        System.out.println(rowCount);

//        Page<Patient> allPatients = patientRepository.findAllPatients(PageRequest.of(0, 10, Sort.by("name")));
//        allPatients.forEach(System.out::println);

        System.out.println(patientRepository.findPatientContact(PageRequest.of(0,10,Sort.by("name"))));
    }

}