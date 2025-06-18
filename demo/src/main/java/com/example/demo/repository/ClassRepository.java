package com.example.demo.repository;

import com.example.demo.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class,Long> {
    List<Class> findByNameContainingIgnoreCase(String name);
}

