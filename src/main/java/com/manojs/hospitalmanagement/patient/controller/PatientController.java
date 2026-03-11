package com.manojs.hospitalmanagement.patient.controller;

import com.manojs.hospitalmanagement.patient.dto.AgeGroupDto;
import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.dto.GenderDto;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import com.manojs.hospitalmanagement.patient.dto.PatientFilterDto;
import com.manojs.hospitalmanagement.patient.dto.PatientRequestDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<PageResponse<PatientResponseDto>> findAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return ResponseEntity.ok(
                patientService.findAllPatients(PageRequest.of(page, size, sort))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> savePatient(
            @Valid @RequestBody PatientRequestDto patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.savePatient(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto patientRequestDto) {

        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDto> partialUpdate(
            @PathVariable Long id,
            @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.partialUpdatePatient(id, patientRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientResponseDto>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(patientService.findByName(name));
    }

    @GetMapping("/blood-groups/count")
    public ResponseEntity<List<BloodGroupCountDTO>> getBloodGroupCount() {
        return ResponseEntity.ok(patientService.bloodGroupCount());
    }

    @GetMapping("/stats/gender")
    public ResponseEntity<GenderDto> getGenderStats() {
        return ResponseEntity.ok(patientService.genderCountStats());
    }

    @GetMapping("/stats/age")
    private ResponseEntity<AgeGroupDto> getAgeStats() {
        return ResponseEntity.ok(patientService.getAgeGroupStats());
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponse<PatientResponseDto>> filterPatients(
            @ModelAttribute PatientFilterDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return ResponseEntity.ok(
                patientService.filterPatients(filter, PageRequest.of(page, size, sort))
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PatientResponseDto>> getRecentPatients(
            @RequestParam(defaultValue = "5") int limit) {

        return ResponseEntity.ok(patientService.getRecentPatients(limit));
    }
}

