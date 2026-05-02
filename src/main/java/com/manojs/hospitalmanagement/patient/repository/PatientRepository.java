package com.manojs.hospitalmanagement.patient.repository;

import com.manojs.hospitalmanagement.patient.dto.AgeGroupDto;
import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.dto.GenderDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    Optional<Patient> findByName(String name);

    @Query("SELECT new com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO(p.bloodGroup,COUNT(p)) From Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountDTO> groupByBloodGroup();

    @Query(value = "SELECT * FROM patient", countQuery = "SELECT COUNT(*) FROM patient",
            nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Query(value = """
            SELECT new com.manojs.hospitalmanagement.patient.dto.GenderDto(
                count(P.gender) FILTER ( WHERE P.gender = 'male'),
                count(P.gender) FILTER ( WHERE P.gender = 'female')
                )
                FROM Patient P
        """)
    GenderDto genderStats();

    @Query(value = """
        SELECT
            COUNT(p.birth_date) FILTER (WHERE EXTRACT(YEAR FROM AGE(p.birth_date)) BETWEEN 0 AND 18) AS children,
            COUNT(p.birth_date) FILTER (WHERE EXTRACT(YEAR FROM AGE(p.birth_date)) BETWEEN 19 AND 40) AS adults,
            COUNT(p.birth_date) FILTER (WHERE EXTRACT(YEAR FROM AGE(p.birth_date)) BETWEEN 41 AND 60) AS middle_age
        FROM patient p
        """, nativeQuery = true)
    AgeGroupDto getAgeGroupStats();

    List<Patient> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN FETCH p.insurance " +
            "LEFT JOIN FETCH p.appointment " +
            "WHERE p.id = :id")
    Optional<Patient> findByIdWithDetails(@Param("id") Long id);
}
