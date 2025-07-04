package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    @Query("SELECT COUNT(a) FROM Appointment a WHERE FUNCTION('MONTH', a.appointmentTime) = :month AND FUNCTION('YEAR', a.appointmentTime) = :year AND a.status IN ('COMPLETED', 'SCHEDULED')")
    long countAppointmentsInMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE FUNCTION('MONTH', a.appointmentTime) = :month AND FUNCTION('YEAR', a.appointmentTime) = :year")
    long countTotalAppointmentsInMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE FUNCTION('MONTH', a.appointmentTime) = :month AND FUNCTION('YEAR', a.appointmentTime) = :year AND a.status = 'COMPLETED'")
    long countCompletedAppointmentsInMonth(@Param("month") int month, @Param("year") int year);

}
