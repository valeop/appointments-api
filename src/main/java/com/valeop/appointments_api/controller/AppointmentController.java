package com.valeop.appointments_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valeop.appointments_api.dto.appointment.AppointmentResponseDTO;
import com.valeop.appointments_api.dto.appointment.CreateAppointmentDTO;
import com.valeop.appointments_api.dto.appointment.UpdateAppointmentDTO;
import com.valeop.appointments_api.service.impl.AppointmentServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentServiceImpl) {
        this.appointmentServiceImpl = appointmentServiceImpl;
    }

    @GetMapping("")
    public String isAppointmentWorking() {
        return "Hello, Appointment working";
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsList() {
        List<AppointmentResponseDTO> appointmentsList = appointmentServiceImpl.getAppointmentsList();
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsList);
    }

    @GetMapping("/all/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Integer appointmentId) {
        AppointmentResponseDTO appointmentFound = appointmentServiceImpl.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentFound);
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody CreateAppointmentDTO createDTO) {
        AppointmentResponseDTO appointmentSaved = appointmentServiceImpl.createAppointment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentSaved);
    }

    @PutMapping(value = "/update", params = "id")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@Valid @RequestBody UpdateAppointmentDTO updateDTO,
            @RequestParam(value = "id") Integer appointmentId) {
        AppointmentResponseDTO appointmentUpdated = appointmentServiceImpl.updateAppointment(updateDTO, appointmentId);
        return ResponseEntity.ok(appointmentUpdated);
    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> deleteAppointment(@PathVariable Integer appointmentId) {
        AppointmentResponseDTO appointmentDeleted = appointmentServiceImpl.deleteAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDeleted);
    }
}