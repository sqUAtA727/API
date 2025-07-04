package com.example.demo.controller;

import com.example.demo.entity.MedicalRecord;
import com.example.demo.service.MedicalRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/add")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord record) {
        return medicalRecordService.addMedicalRecord(record);
    }

    @GetMapping("/history/{patientId}")
    public Page<MedicalRecord> getMedicalHistory(
            @PathVariable Long patientId,
            @RequestParam int page,
            @RequestParam int size) {
        return medicalRecordService.getMedicalHistoryByPatientId(patientId, PageRequest.of(page, size));
    }

    @GetMapping("/medical-records/statistics/most-common-disease")
    public String getMostCommonDisease(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return medicalRecordService.getMostCommonDisease(startDate, endDate);
    }

}
