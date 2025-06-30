package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/allStudent")
    public List<Student> getAllStudents(@RequestParam int page, @RequestParam int size) {
        return studentService.getAllStudents(page, size);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchStudents(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @RequestParam(required = false) String gender, @RequestParam(required = false) Integer ageMin, @RequestParam(required = false) Integer ageMax, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        return studentService.searchStudents(name, email, gender, ageMin, ageMax, page, size).getContent();
    }

    @GetMapping("/class/{classId}")
    public List<Student> getByClassId(@PathVariable Long classId) {
        return studentService.getStudentsByClassId(classId);
    }

    @PutMapping("/transfer/{studentId}/{newClassId}")
    public Student transfer(@PathVariable Long studentId, @PathVariable Long newClassId) {
        return studentService.transferStudent(studentId, newClassId);
    }

    @GetMapping("/find/{studentName}")
    public List<Student> findByStudentName(@PathVariable String studentName) {
        return studentService.findStudentByName(studentName);
    }
}
