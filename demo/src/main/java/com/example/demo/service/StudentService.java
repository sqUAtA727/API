package com.example.demo.service;

import com.example.demo.entity.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Long classId, Student student);
    List<Student> addManyStudents(Long classId, List<Student> students);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    List<Student> getStudentsByClassId(Long classId);
    Student transferStudent(Long studentId, Long newClassId);
    List<Student> searchByStudentName(String name);
    List<Student> searchByClassName(String className);
}
