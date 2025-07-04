package com.example.demo.controller;

import com.example.demo.dto.PatientRequest;
import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/add")
    public Patient addPatient(@RequestBody PatientRequest request) {
        return patientService.addPatient(request);
    }

    @PostMapping("/add-many")
    public List<Patient> addManyPatient(@RequestBody List<PatientRequest> requests) { return patientService.addManyPatients(requests); }

    @GetMapping("/all")
    public Page<Patient> getAllPatients(@RequestParam int page, @RequestParam int size) {
        return patientService.findAllPatients(PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public Page<Patient> searchPatient(
            @RequestParam(required = false) Long id, // để có thể đưa kiểu null
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size) {
        return patientService.searchPatients(id, name, gender, phoneNumber, startDate, endDate, PageRequest.of(page, size));
    }
}
