package com.manojs.hospitalmanagement.department.service.impl;

import com.manojs.hospitalmanagement.department.dto.DepartmentResponseDto;
import com.manojs.hospitalmanagement.department.entity.Department;
import com.manojs.hospitalmanagement.department.mapper.DepartmentMapper;
import com.manojs.hospitalmanagement.department.repository.DepartmentRepository;
import com.manojs.hospitalmanagement.department.service.DepartmentService;
import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.doctor.entity.Doctor;
import com.manojs.hospitalmanagement.doctor.mapper.DoctorMapper;
import com.manojs.hospitalmanagement.doctor.repository.DoctorRepository;
import com.manojs.hospitalmanagement.doctor.service.DoctorService;
import com.manojs.hospitalmanagement.exception.ResourceNotFoundException;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public PageResponse<DepartmentResponseDto> getAllDepartments(final Pageable pageable) {
        Page<Department> allDepartments = departmentRepository.findAllDepartments(pageable);
        Page<DepartmentResponseDto> map = allDepartments.map(departmentMapper::toDto);
        return PageResponse.from(map);
    }

    @Override
    @Transactional
    public DoctorResponseDto addDoctorToDepartment(final Long doctorId, final Long departmentId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new ResourceNotFoundException("Doctor not found of id: "+ doctorId));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found of id: "+ doctorId));

        department.getDoctors().add(doctor);
        doctor.getDepartments().add(department);   // 🔥 IMPORTANT

        return doctorMapper.toDto(doctor);
    }
}
