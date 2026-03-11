package com.manojs.hospitalmanagement.department.controller;

import com.manojs.hospitalmanagement.department.dto.DepartmentResponseDto;
import com.manojs.hospitalmanagement.department.service.DepartmentService;
import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<PageResponse<DepartmentResponseDto>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        return ResponseEntity.ok(departmentService.getAllDepartments(PageRequest.of(page,size,sort)));
    }
    @PostMapping("/{deptId}/doctor/{doctorId}")
    public ResponseEntity<DoctorResponseDto> addDoctorToDepartment(
            @PathVariable Long deptId,
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(departmentService.addDoctorToDepartment(doctorId,deptId));
    }
}