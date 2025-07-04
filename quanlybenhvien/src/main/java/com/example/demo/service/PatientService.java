package com.example.demo.service;

import com.example.demo.dto.PatientRequest;
import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    private void validatePatientRequest(PatientRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên bệnh nhân không được để trống");
        }
        if (request.getGender() == null || request.getGender().trim().isEmpty()) {
            throw new IllegalArgumentException("Giới tính không được để trống");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        // Nếu bạn muốn kiểm tra định dạng số điện thoại đơn giản
        if (!request.getPhoneNumber().matches("\\d{9,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
    }


    public Patient addPatient(PatientRequest request) {
        validatePatientRequest(request);
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setGender(request.getGender());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setAddress(request.getAddress());
        return patientRepository.save(patient);
    }

    public List<Patient> addManyPatients(List<PatientRequest> requests) {
        List<Patient> patients = new ArrayList<>();
        for (PatientRequest request : requests) {
            validatePatientRequest(request);
            Patient patient = new Patient();
            patient.setName(request.getName());
            patient.setGender(request.getGender());
            patient.setPhoneNumber(request.getPhoneNumber());
            patient.setAddress(request.getAddress());
            patients.add(patient);
        }
        return patientRepository.saveAll(patients);
    }

    public Page<Patient> findAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Page<Patient> searchPatients(
            Long id,
            String name,
            String gender,
            String phoneNumber,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
//      Khai báo cho phép truy vấn động
//      Điều kiện áp dụng cho patient
        Specification<Patient> specification = (root, query, cb) -> {
//          Khai báo danh sách chứa các điều kiện
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (gender != null && !gender.isEmpty()) {
                predicates.add(cb.equal(root.get("gender"), gender));
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                predicates.add(cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return patientRepository.findAll(specification, pageable);
    }
}
