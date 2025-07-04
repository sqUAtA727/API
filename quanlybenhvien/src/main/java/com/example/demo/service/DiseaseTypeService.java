package com.example.demo.service;

import com.example.demo.entity.DiseaseType;
import com.example.demo.repository.DiseaseTypeRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseTypeService {
    private final DiseaseTypeRepository diseaseTypeRepository;

    public DiseaseTypeService(DiseaseTypeRepository diseaseTypeRepository) {
        this.diseaseTypeRepository = diseaseTypeRepository;
    }

    public DiseaseType addDiseaseType (DiseaseType diseaseType) {
        if (diseaseTypeRepository.existsByNameIgnoreCase(diseaseType.getName())) {
            throw new IllegalArgumentException("Tên loại bệnh đã tồn tại");
        }
        return diseaseTypeRepository.save(diseaseType);
    }

    public List<DiseaseType> addManyDiseaseType (List<DiseaseType> diseaseTypes) {
        for (DiseaseType diseaseType : diseaseTypes) {
            if (diseaseTypeRepository.existsByNameIgnoreCase(diseaseType.getName())) {
                throw new IllegalArgumentException("Tên loại bệnh đã tồn tại");
            }
        }
        return diseaseTypeRepository.saveAll(diseaseTypes);
    }

    public Page<DiseaseType> findAllDiseaseType (Pageable pageable) {
        return diseaseTypeRepository.findAll(pageable);
    }
}
