package com.example.demo.service;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.dto.PatientRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Status;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment addAppointment(AppointmentRequest request) {
        if (!patientRepository.existsById(request.getPatientId())) {
            throw new IllegalArgumentException("Bệnh nhân không tồn tại");
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(Status.SCHEDULED);
        appointment.setNote(request.getNote());
        appointment.setCreateAt(LocalDate.now());

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> addManyAppointments(List<AppointmentRequest> requests) {
        List<Appointment> appointments = new ArrayList<>();
        for (AppointmentRequest request : requests) {
            if (!patientRepository.existsById(request.getPatientId())) {
                throw new IllegalArgumentException("Bệnh nhân không tồn tại");
            }

            Appointment appointment = new Appointment();
            appointment.setPatientId(request.getPatientId());
            appointment.setAppointmentTime(request.getAppointmentTime());
            appointment.setStatus(Status.SCHEDULED);
            appointment.setNote(request.getNote());
            appointment.setCreateAt(LocalDate.now());
        }
        return appointmentRepository.saveAll(appointments);
    }

    public Appointment cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch hẹn"));
        appointment.setStatus(Status.CANCELED);
        return appointmentRepository.save(appointment);
    }

    public Appointment completeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch hẹn"));
        appointment.setStatus(Status.COMPLETED);
        return appointmentRepository.save(appointment);
    }

    public Appointment notShowAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch hẹn"));
        appointment.setStatus(Status.NOT_SHOW);
        return appointmentRepository.save(appointment);
    }

    public Page<Appointment> findAllAppointment(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    public Page<Appointment> searchAppointments(
            Long id,
            Long patientId,
            String patientName,
            LocalDateTime startAppointmentTime,
            LocalDateTime endAppointmentTime,
            Status status,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
        Specification<Appointment> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            if (patientId != null) {
                predicates.add(cb.equal(root.get("patient").get("id"), patientId));
            }

//           Tìm theo tên bênh nhân thì join bảng patients
            if (patientName != null && !patientName.isEmpty()) {
                Join<Appointment, Patient> patientJoin = root.join("patient", JoinType.INNER);
                predicates.add(cb.like(cb.lower(patientJoin.get("name")), "%" + patientName.toLowerCase() + "%"));
            }

            if (startAppointmentTime != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("appointmentTime"), startAppointmentTime));
            }

            if (endAppointmentTime != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("appointmentTime"), endAppointmentTime));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), startDate));
            }

            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return appointmentRepository.findAll(specification, pageable);
    }

    public long countAppointmentsThisMonth() {
        LocalDate now = LocalDate.now();
        return appointmentRepository.countAppointmentsInMonth(now.getMonthValue(), now.getYear());
    }

    public double calculateAverageAppointmentsPerDayThisMonth() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        long totalAppointments = appointmentRepository.countTotalAppointmentsInMonth(month, year);

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        return (double) totalAppointments / daysInMonth;
    }

    public double calculateAverageCompletedAppointmentsPerDayThisMonth() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        long totalCompletedAppointments = appointmentRepository.countCompletedAppointmentsInMonth(month, year);

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        return (double) totalCompletedAppointments / daysInMonth;
    }

    public double calculateAttendanceRateThisMonth() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        long totalAppointments = appointmentRepository.countTotalAppointmentsInMonth(month, year);
        long completedAppointments = appointmentRepository.countCompletedAppointmentsInMonth(month, year);

        if (totalAppointments == 0) {
            return 0.0;
        }

        return ((double) completedAppointments / totalAppointments) * 100;
    }
}
