package com.example.demo.service;

import com.example.demo.entity.Classes;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentServiceImpl(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    public Student addStudent(Long classId, Student student) {
        Classes classes = classRepository.findById(classId).orElseThrow();
        student.setClassroom(classes);
        return studentRepository.save(student);
    }

    public List<Student> addManyStudents(Long classId, List<Student> students) {
        Classes classes = classRepository.findById(classId).orElseThrow();
        students.forEach(student -> student.setClassroom(classes));
        return studentRepository.saveAll(students);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByClassId(Long classId) {
        Classes classes = classRepository.findById(classId).orElseThrow();
        return classes.getStudents();
    }

    public Student transferStudent(Long studentId, Long newClassId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Classes classes = classRepository.findById(newClassId).orElseThrow();
        student.setClassroom(classes);
        return studentRepository.save(student);
    }
}
