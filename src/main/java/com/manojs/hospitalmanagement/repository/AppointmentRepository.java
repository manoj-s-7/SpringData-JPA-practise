package com.manojs.hospitalmanagement.repository;

import com.manojs.hospitalmanagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}