package com.example.demo.service;

import com.example.demo.repository.ClassRepository;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Class;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Class addClass(Class cls) {
        return classRepository.save(cls);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public List<Class> searchByName(String name) {
        return classRepository.findByNameContainingIgnoreCase(name);
    }
}
