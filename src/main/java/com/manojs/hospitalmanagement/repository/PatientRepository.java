package com.manojs.hospitalmanagement.repository;

import com.manojs.hospitalmanagement.dto.BloodGroupByPatientsDTO;
import com.manojs.hospitalmanagement.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.dto.PatientContactDTO;
import com.manojs.hospitalmanagement.entity.type.BloodGroupType;
import com.manojs.hospitalmanagement.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByName(String name);

    //    JPQL
    @Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("SELECT new com.manojs.hospitalmanagement.dto.BloodGroupCountDTO(p.bloodGroup,COUNT(p)) From Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountDTO> groupByBloodGroup();

    @Query("""
            SELECT new com.manojs.hospitalmanagement.dto.BloodGroupByPatientsDTO(
                   p.bloodGroup,
                   COUNT(p),
                   SUM(CASE WHEN p.gender = 'male' THEN 1 ELSE 0 END),
                   SUM(CASE WHEN p.gender = 'female' THEN 1 ELSE 0 END),
                   MIN(p.createdAt),
                   MAX(p.createdAt)
            )
            FROM Patient p
            WHERE p.createdAt >= :cutoffDate
              AND p.birthDate IS NOT NULL
              AND p.gender IS NOT NULL
            GROUP BY p.bloodGroup
            HAVING COUNT(p) > 3
            ORDER BY COUNT(p) DESC
            """)
    List<BloodGroupByPatientsDTO> getBloodGroupStats(
            @Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("""
            SELECT p FROM Patient p WHERE p.birthDate > :localDate
            """)
    List<Patient> findPatientByBornAfterDate(LocalDate localDate);

    //    native query
    @Query(value = "SELECT * from patients_table where gender = 'male'", nativeQuery = true)
    List<Patient> findALlMalePatients();

    @Transactional
    @Modifying
    @Query(value = """
            update patients_table
            set name = :name
            where id = :id
            """, nativeQuery = true)
    int updateNameByID(@Param("name") String name, @Param("id") Long id);

    @Query(value = "SELECT * FROM patients_table", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Query("""
            SELECT new com.manojs.hospitalmanagement.dto.PatientContactDTO(p.name,p.email) 
                    FROM Patient p
        """)
    List<PatientContactDTO> findPatientContact(Pageable pageable);

}
