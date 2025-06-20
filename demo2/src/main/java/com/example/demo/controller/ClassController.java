package com.example.demo.controller;

import com.example.demo.entity.Classes;
import com.example.demo.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/add")
    public Classes addClass(@RequestBody Classes classes) {
        return classService.addClass(classes);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        System.out.println("Deleted Class: " + id);
    }

    @GetMapping("/search")
    public List<Classes> searchClassesByName(@RequestParam String name) {
        return classService.searchByName(name);
    }
}
