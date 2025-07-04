package com.example.demo.repository;

import com.example.demo.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {
    Page<MedicalRecord> findByPatientIdOrderByIdDesc(Long patientId, Pageable pageable);

    @Query(value = """
    SELECT dt.name AS diseaseName, COUNT(*) AS count
    FROM medical_records mr
    JOIN disease_types dt ON mr.disease_type_id = dt.id
    WHERE mr.record_date BETWEEN :startDate AND :endDate
    GROUP BY dt.name
    ORDER BY count DESC
    LIMIT 1
""", nativeQuery = true)
    List<Object[]> findMostCommonDiseaseBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
