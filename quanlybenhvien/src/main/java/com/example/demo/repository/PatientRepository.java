package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
//  Cho phép dùng specification
@Query(value = """
    SELECT p.* FROM patients p
    JOIN doctor_patient dp ON p.id = dp.patient_id
    WHERE dp.doctor_id = :doctorId
""", nativeQuery = true)
    Page<Patient> findAllByDoctorId(Long doctorId, Pageable pageable);
}
