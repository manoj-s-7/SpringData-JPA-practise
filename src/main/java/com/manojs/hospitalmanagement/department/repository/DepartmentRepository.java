package com.manojs.hospitalmanagement.department.repository;

import com.manojs.hospitalmanagement.department.entity.Department;
import com.manojs.hospitalmanagement.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT * FROM department", countQuery = "SELECT COUNT(*) FROM department",
            nativeQuery = true)
    Page<Department> findAllDepartments(Pageable pageable);

}