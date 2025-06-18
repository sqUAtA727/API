package com.example.demo.controller;

import com.example.demo.service.ClassService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Class;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/add")
    public Class add(@RequestBody Class cls) {
        return classService.addClass(cls);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        classService.deleteClass(id);
    }

    @GetMapping("/search")
    public List<Class> search(@RequestParam String name) {
        return classService.searchByName(name);
    }
}
