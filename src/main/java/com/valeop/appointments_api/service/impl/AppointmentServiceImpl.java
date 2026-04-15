package com.valeop.appointments_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.valeop.appointments_api.dto.appointment.AppointmentResponseDTO;
import com.valeop.appointments_api.dto.appointment.CreateAppointmentDTO;
import com.valeop.appointments_api.dto.appointment.UpdateAppointmentDTO;
import com.valeop.appointments_api.exceptions.AccessDeniedException;
import com.valeop.appointments_api.exceptions.ResourceNotFoundException;
import com.valeop.appointments_api.mapper.AppointmentMapper;
import com.valeop.appointments_api.model.Appointment;
import com.valeop.appointments_api.model.Doctor;
import com.valeop.appointments_api.model.DoctorService;
import com.valeop.appointments_api.model.User;
import com.valeop.appointments_api.model.UserSecurityDetails;
import com.valeop.appointments_api.model.enums.ServiceType;
import com.valeop.appointments_api.repository.AppointmentRepository;
import com.valeop.appointments_api.repository.DoctorRepository;
import com.valeop.appointments_api.repository.DoctorServiceRepository;
import com.valeop.appointments_api.repository.UserRepository;
import com.valeop.appointments_api.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
        private final AppointmentRepository appointmentRepository;
        private final UserRepository userRepository;
        private final DoctorServiceRepository doctorServiceRepository;
        private final DoctorRepository doctorRepository;

        private static final String MESSAGE = "Appointment does not exist with ID #";

        @Autowired
        public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository,
                        DoctorServiceRepository doctorServiceRepository, DoctorRepository doctorRepository) {
                this.appointmentRepository = appointmentRepository;
                this.userRepository = userRepository;
                this.doctorServiceRepository = doctorServiceRepository;
                this.doctorRepository = doctorRepository;
        }

        @Override
        public List<AppointmentResponseDTO> getAppointmentsList() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth == null || !auth.isAuthenticated()) {
                        throw new ResourceNotFoundException("User authenticated was not found.");
                }

                String email = auth.getName();

                String roleAuth = auth.getAuthorities().toString();

                User userFound = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User" + auth.getName() + " does not exist"));

                if (roleAuth.contains("ROLE_DOCTOR")) {
                        Doctor doctorFound = doctorRepository.findByUser(userFound)
                                        .orElseThrow(() -> new ResourceNotFoundException("Doctor does not exist."));

                        DoctorService doctorServiceFound = doctorServiceRepository.findByDoctor(doctorFound)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Does not exist a service for you."));

                        List<Appointment> doctorListFound = appointmentRepository
                                        .findAllByDoctorService(doctorServiceFound);

                        if (doctorListFound.isEmpty()) {
                                throw new ResourceNotFoundException("You have no pending appointments.");
                        }

                        return doctorListFound.stream().map(AppointmentMapper::toResponseDTO).toList();
                }

                List<Appointment> patientListFound = appointmentRepository.findAllByUser(userFound);

                if (patientListFound.isEmpty()) {
                        throw new ResourceNotFoundException("You have no appointments.");
                }

                return patientListFound.stream().map(AppointmentMapper::toResponseDTO).toList();
        }

        @Override
        public AppointmentResponseDTO getAppointmentById(Integer appointmentId) {
                Appointment appointmentFound = appointmentRepository.findByAppointmentId(appointmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + appointmentId));
                return AppointmentMapper.toResponseDTO(appointmentFound);
        }

        @Override
        public AppointmentResponseDTO createAppointment(CreateAppointmentDTO createDTO) {
                User userFound = userRepository.findByUserId(createDTO.user().getUserId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User does not exist. Try another one"));

                DoctorService doctorServiceFound = doctorServiceRepository
                                .findByDoctorServiceId(createDTO.doctorService().getDoctorServiceId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "DoctorService does not exist. Try another one"));

                validatePermission(doctorServiceFound, new UserSecurityDetails(userFound).getUsername());

                Appointment newAppointment = AppointmentMapper.createFromDTO(createDTO, userFound,
                                doctorServiceFound);

                Appointment appointmentSaved = appointmentRepository.save(newAppointment);
                return AppointmentMapper.toResponseDTO(appointmentSaved);
        }

        @Override
        public AppointmentResponseDTO updateAppointment(UpdateAppointmentDTO updateDTO, Integer appointmentId) {
                Appointment appointmentFound = appointmentRepository.findByAppointmentId(appointmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + appointmentId));

                if (updateDTO.user() != null) {
                        Integer userId = updateDTO.user().getUserId();
                        User userFound = userRepository.findByUserId(userId)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "User does not exist. Try another one"));
                        appointmentFound.setUser(userFound);
                }

                if (updateDTO.doctorService() != null) {
                        Integer doctorServiceId = updateDTO.doctorService().getDoctorServiceId();
                        DoctorService doctorServiceFound = doctorServiceRepository
                                        .findByDoctorServiceId(doctorServiceId)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "DoctorService does not exist. Try another one"));
                        appointmentFound.setDoctorService(doctorServiceFound);
                }

                AppointmentMapper.updateFromDTO(updateDTO, appointmentFound);
                Appointment appointmentUpdated = appointmentRepository.save(appointmentFound);
                return AppointmentMapper.toResponseDTO(appointmentUpdated);
        }

        @Override
        public AppointmentResponseDTO deleteAppointment(Integer appointmentId) {
                Appointment appointmentFound = appointmentRepository.findByAppointmentId(appointmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE + appointmentId));

                appointmentRepository.deleteById(appointmentId);
                return AppointmentMapper.toResponseDTO(appointmentFound);
        }

        @Override
        public void validatePermission(DoctorService doctorService, String username) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth == null || !auth.isAuthenticated()) {
                        throw new ResourceNotFoundException("User authenticated was not found.");
                }

                boolean isPatient = auth.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));

                if (isPatient) {
                        if (!username.equals(auth.getName())) {
                                throw new AccessDeniedException(
                                                "You are not allowed to create an appointment for another user.");
                        } else if (!ServiceType.PATIENT_ALLOWED
                                        .contains(doctorService.getService().getServiceName())) {
                                throw new AccessDeniedException(
                                                "Patients can only book general, dental or ophthalmology appointments.");
                        }
                }
        }
}
