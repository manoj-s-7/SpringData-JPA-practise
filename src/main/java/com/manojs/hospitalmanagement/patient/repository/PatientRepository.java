package com.manojs.hospitalmanagement.patient.repository;

import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByName(String name);

    @Query("SELECT new com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO(p.bloodGroup,COUNT(p)) From Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountDTO> groupByBloodGroup();

    @Query(value = "SELECT * FROM patients_table", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);
}
