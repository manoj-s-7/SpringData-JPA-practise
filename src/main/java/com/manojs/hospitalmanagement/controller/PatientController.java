package com.manojs.hospitalmanagement.controller;

import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        List<Patient> allPatients = patientService.getAllPatients();
        if (allPatients != null){
            return ResponseEntity.ok(allPatients);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id){
        Patient patientById = patientService.getAllPatientById(id).orElse(null);
        if (patientById != null){
            return ResponseEntity.ok(patientById);
        }
        return ResponseEntity.notFound().build();
    }
}
