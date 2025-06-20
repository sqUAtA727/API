package com.example.demo.service;

import com.example.demo.entity.Classes;

import java.util.List;

public interface ClassService {
    Classes addClass(Classes classes);
    void deleteClass(Long ClassId);
    List<Classes> searchByName(String name);
}
