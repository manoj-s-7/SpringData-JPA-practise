package com.manojs.hospitalmanagement.controller;

import com.manojs.hospitalmanagement.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/patient")
public class PatientController {

    private final PatientService patientService;

//    @GetMapping
//    public ResponseEntity<List<Patient>> getAllPatients() {
//        return ResponseEntity.ok(patientService.getAllPatients());
//    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.savePatient(patient));
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<Patient>> findByName(@PathVariable String name){
        return ResponseEntity.ok(patientService.findByName(name));
    }

    @GetMapping(path = "/bloodGroupCount")
    public ResponseEntity<List<BloodGroupCountDTO>> getBloodGroupCount(){
        return ResponseEntity.ok(patientService
                .bloodGroupCount());
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> findAllPatients(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "createdAt") String sortField,
                                                         @RequestParam(defaultValue = "desc")  String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        return ResponseEntity.ok(patientService.findAllPatients(PageRequest.of(page,size,sort)));
    }
}
