package com.example.demo.service;

import com.example.demo.entity.Class;

import java.util.List;

public interface ClassService {
    Class addClass(Class cls);
    void deleteClass(Long id);
    List<Class> searchByName(String name);
}
