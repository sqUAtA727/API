package com.example.demo.controller;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Status;
import com.example.demo.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/add")
    public Appointment addAppointment(@RequestBody AppointmentRequest request) {
        return appointmentService.addAppointment(request);
    }

    @PostMapping("/add-many")
    public List<Appointment> addManyAppointment(@RequestBody List<AppointmentRequest> requests) {
        return appointmentService.addManyAppointments(requests);
    }

    @PutMapping("/cancel/{id}")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return appointmentService.cancelAppointment(id);
    }

    @PutMapping("/complete/{id}")
    public Appointment completeAppointment(@PathVariable Long id) {
        return appointmentService.completeAppointment(id);
    }

    @PutMapping("/not-show/{id}")
    public Appointment notShowAppointment(@PathVariable Long id) {
        return appointmentService.notShowAppointment(id);
    }

    @GetMapping("/all")
    public Page<Appointment> getAllAppointments(
            @RequestParam int page,
            @RequestParam int size) {
        return appointmentService.findAllAppointment(PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public Page<Appointment> searchAppointments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAppointmentTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAppointmentTime,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size) {

        return appointmentService.searchAppointments(
                id, patientId, patientName,
                startAppointmentTime, endAppointmentTime,
                status, startDate, endDate, PageRequest.of(page, size)
        );
    }

    @GetMapping("/appointments/statistics/this-month")
    public long countAppointmentsThisMonth() {
        return appointmentService.countAppointmentsThisMonth();
    }

    @GetMapping("/appointments/statistics/average-per-day")
    public double getAverageAppointmentsPerDayThisMonth() {
        return appointmentService.calculateAverageAppointmentsPerDayThisMonth();
    }

    @GetMapping("/appointments/statistics/average-completed-per-day")
    public double getAverageCompletedAppointmentsPerDayThisMonth() {
        return appointmentService.calculateAverageCompletedAppointmentsPerDayThisMonth();
    }

    @GetMapping("/appointments/statistics/attendance-rate")
    public double getAttendanceRateThisMonth() {
        return appointmentService.calculateAttendanceRateThisMonth();
    }


}
