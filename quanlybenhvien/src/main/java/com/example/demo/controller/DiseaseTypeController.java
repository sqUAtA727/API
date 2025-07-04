package com.example.demo.controller;

import com.example.demo.entity.DiseaseType;
import com.example.demo.service.DiseaseTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disease-types")
public class DiseaseTypeController {
    private final DiseaseTypeService diseaseTypeService;

    public DiseaseTypeController(DiseaseTypeService diseaseTypeService) {
        this.diseaseTypeService = diseaseTypeService;
    }

    @PostMapping("/add")
    public DiseaseType addDiseaseType(@RequestBody DiseaseType diseaseType) {
        return diseaseTypeService.addDiseaseType(diseaseType);
    }

    @PostMapping("/add-many")
    public List<DiseaseType> addManyDiseaseType(@RequestBody List<DiseaseType> diseaseTypes) {
        return diseaseTypeService.addManyDiseaseType(diseaseTypes);
    }

    @GetMapping("/all")
    public Page<DiseaseType> findAll(@RequestParam int page,@RequestParam int size) {
        return diseaseTypeService.findAllDiseaseType(PageRequest.of(page, size));
    }
}
