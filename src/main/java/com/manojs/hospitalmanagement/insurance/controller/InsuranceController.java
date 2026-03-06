package com.manojs.hospitalmanagement.insurance.controller;

import com.manojs.hospitalmanagement.insurance.dto.InsuranceRequestDto;
import com.manojs.hospitalmanagement.insurance.dto.InsuranceResponseDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.insurance.service.InsuranceService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/insurance")
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<InsuranceResponseDto> getPatientInsurance(@PathVariable Long id) {
        return ResponseEntity.ok(insuranceService.getPatientInsurance(id));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<PatientResponseDto> addInsurance(@RequestBody InsuranceRequestDto insurance, @PathVariable Long id) {
        return new ResponseEntity<>(insuranceService.assignInsuranceToPatient(insurance, id), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PatientResponseDto> updateInsurance(@RequestBody InsuranceRequestDto insurance, @PathVariable Long id) {
        return new ResponseEntity<>(insuranceService.updateInsurance(id, insurance), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        try {
            insuranceService.deleteInsurance(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
