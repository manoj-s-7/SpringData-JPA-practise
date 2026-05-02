package com.manojs.hospitalmanagement.appointment.controller;


import com.manojs.hospitalmanagement.appointment.dto.AppointmentRequestDTO;
import com.manojs.hospitalmanagement.appointment.dto.AppointmentResponseDTO;
import com.manojs.hospitalmanagement.appointment.service.AppointmentService;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> addNewAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(appointmentRequestDTO));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> reassignDoctor(@PathVariable Long id, @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.ok(appointmentService.reassignAppointment(appointmentRequestDTO,id));
    }

    @GetMapping(path = "/doctor/{doctorId}")
    public ResponseEntity<PageResponse<AppointmentResponseDTO>> getAllByDoctorId(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentTime") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(doctorId,PageRequest.of(page, size, sort)));
    }

    @GetMapping(path = "/patient/{patientId}")
    public ResponseEntity<PageResponse<AppointmentResponseDTO>> getAllByPatientId(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentTime") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId,PageRequest.of(page, size, sort)));
    }
}
