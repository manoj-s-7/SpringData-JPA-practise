package com.manojs.hospitalmanagement.doctor.controller;

import com.manojs.hospitalmanagement.doctor.dto.DoctorFilterDto;
import com.manojs.hospitalmanagement.doctor.dto.DoctorRequestDto;
import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.doctor.service.DoctorService;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<PageResponse<DoctorResponseDto>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return ResponseEntity.ok(doctorService.getAllDoctors(PageRequest.of(page, size, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> saveDoctor(
            @RequestBody @Valid DoctorRequestDto doctorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.saveDoctor(doctorRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long id,
            @RequestBody @Valid DoctorRequestDto doctorRequestDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> partialUpdateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequestDto doctorRequestDto) {
        return ResponseEntity.ok(doctorService.partialUpdateDoctor(id, doctorRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponse<DoctorResponseDto>> filterDoctors(
            @ModelAttribute DoctorFilterDto filter,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(doctorService.filterDoctors(filter, pageable));
    }
}