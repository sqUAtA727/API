package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Long classId, Student student) {
        student.setClassId(classId);
        return studentRepository.save(student);
    }

    public List<Student> addManyStudents(Long classId, List<Student> students) {
        students.forEach(student -> student.setClassId(classId));
        return studentRepository.saveAll(students);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByClassId(Long classId) {
        return studentRepository.findStudentsByClassId(classId);
    }

    public Student transferStudent(Long studentId, Long newClassId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        student.setClassId(newClassId);
        return studentRepository.save(student);
    }

    public List<Student> findStudentByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
}
