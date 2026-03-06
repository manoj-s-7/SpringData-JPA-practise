package com.manojs.hospitalmanagement.appointment.repository;

import com.manojs.hospitalmanagement.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}