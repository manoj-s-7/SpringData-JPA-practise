package com.manojs.hospitalmanagement.department.service;

import com.manojs.hospitalmanagement.department.dto.DepartmentResponseDto;
import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService {

    PageResponse<DepartmentResponseDto> getAllDepartments(Pageable pageable);

    DoctorResponseDto addDoctorToDepartment(Long doctorId,Long departmentId);
}
