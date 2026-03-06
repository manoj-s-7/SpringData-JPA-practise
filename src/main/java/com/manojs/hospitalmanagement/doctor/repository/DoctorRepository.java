package com.manojs.hospitalmanagement.doctor.repository;

import com.manojs.hospitalmanagement.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}