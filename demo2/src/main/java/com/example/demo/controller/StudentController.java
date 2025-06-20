package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add/{classId}")
    public Student addStudent(@PathVariable Long classId, @RequestBody Student student) {
        return studentService.addStudent(classId, student);
    }

    @PostMapping("/add-many/{classId}")
    public List<Student> addManyStudent(@PathVariable Long classId, @RequestBody List<Student> students) {
        return studentService.addManyStudents(classId, students);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/class/{classId}")
    public List<Student> getByClassId(@PathVariable Long classId) {
        return studentService.getStudentsByClassId(classId);
    }

    @PutMapping("/transfer/{studentId}/{newClassId}")
    public Student transfer(@PathVariable Long studentId, @PathVariable Long newClassId) {
        return studentService.transferStudent(studentId, newClassId);
    }
}
