package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.Class;
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
        Class cls = classRepository.findById(classId).orElseThrow();
        student.setClassroom(cls);
        return studentRepository.save(student);
    }

    public List<Student> addManyStudents(Long classId, List<Student> students) {
        Class cls = classRepository.findById(classId).orElseThrow();
        for (Student student : students) {
            student.setClassroom(cls);
        }
        return studentRepository.saveAll(students);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByClassId(Long classId) {
        return studentRepository.findByClassId(classId);
    }

    public Student transferStudent(Long studentId, Long newClassId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Class newClass = classRepository.findById(newClassId).orElseThrow();
        student.setClassroom(newClass);
        return studentRepository.save(student);
    }

    public List<Student> searchByStudentName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Student> searchByClassName(String className) {
        return studentRepository.findByClassroomNameContainingIgnoreCase(className);
    }
}
