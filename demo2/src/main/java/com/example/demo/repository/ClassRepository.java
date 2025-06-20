package com.example.demo.repository;

import com.example.demo.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Classes,Long> {
    List<Classes> findByNameContainingIgnoreCase(String name);
}
