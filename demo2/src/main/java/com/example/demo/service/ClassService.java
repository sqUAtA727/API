package com.example.demo.service;

import com.example.demo.entity.Classes;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;

    public ClassService(ClassRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    public Classes addClass(Classes classes) {
        return classRepository.save(classes);
    }

    public void deleteClass(Long classId) {
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            if (student.getClassId().equals(classId)) {
                studentRepository.delete(student);
            }
        }
        classRepository.deleteById(classId);
    }

    public List<Classes> searchByName(String name) {
        return classRepository.findByNameContainingIgnoreCase(name);
    }
}
