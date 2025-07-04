package com.example.demo.repository;

import com.example.demo.entity.DiseaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseTypeRepository extends JpaRepository<DiseaseType, Long> {
    boolean existsByNameIgnoreCase(String name);
}
