package com.example.demo.service;

import com.example.demo.entity.MedicalRecord;
import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) {
        if (!patientRepository.existsById(record.getPatientId())) {
            throw new IllegalArgumentException("Bệnh nhân không tồn tại");
        }
        record.setRecordDate(LocalDate.now());
        return medicalRecordRepository.save(record);
    }

    public Page<MedicalRecord> getMedicalHistoryByPatientId(Long patientId, Pageable pageable) {
        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException("Bệnh nhân không tồn tại");
        }
        return medicalRecordRepository.findByPatientIdOrderByIdDesc(patientId, pageable);
    }

    public String getMostCommonDisease(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = medicalRecordRepository.findMostCommonDiseaseBetween(startDate, endDate);
        if (results == null || results.isEmpty()) {
            return "Không có dữ liệu trong khoảng thời gian này";
        }
        Object[] row = results.get(0);
        String diseaseName = String.valueOf(row[0]);
        long count = ((Number) row[1]).longValue();

        return String.format("Loại bệnh hay gặp nhất là '%s' với %d lượt", diseaseName, count);
    }

}
