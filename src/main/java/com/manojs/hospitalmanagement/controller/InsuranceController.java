package com.manojs.hospitalmanagement.controller;

import com.manojs.hospitalmanagement.entity.Insurance;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Insurance> getPatientInsurance(@PathVariable Long id){
        return ResponseEntity.ok(insuranceService.getPatientInsurance(id));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<Patient> addInsurance(@RequestBody Insurance insurance,@PathVariable  Long id){
        return new ResponseEntity<>(insuranceService.assignInsuranceToPatient(insurance,id), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Patient> updateInsurance(@RequestBody Insurance insurance,@PathVariable Long id){
        return new ResponseEntity<>(insuranceService.updateInsurance(id,insurance),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsurance(@PathVariable Long id){
        try {
            insuranceService.deleteInsurance(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
