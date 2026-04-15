package com.valeop.appointments_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeop.appointments_api.model.Appointment;
import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAll();

    Optional<Appointment> findByAppointmentId(Integer appointmentId);

    List<Appointment> findAllByUser(User user);

    List<Appointment> findAllByDoctorService(DoctorService doctorService);
}
