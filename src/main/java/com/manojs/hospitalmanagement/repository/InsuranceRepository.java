package com.manojs.hospitalmanagement.repository;

import com.manojs.hospitalmanagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Optional<Insurance> findByPatientId(Long patientId);
}