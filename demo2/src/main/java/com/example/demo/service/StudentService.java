package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.persistence.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public StudentService(StudentRepository studentRepository) {
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

    public List<Student> getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable).getContent();
    }

    public Page<Map<String, Object>> searchStudents (String name, String email, String gender, Integer ageMin, Integer ageMax, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, name, age, email, phone, gender FROM student WHERE 1=1");
        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM student WHERE 1=1");

        Map<String, Object> params = new HashMap<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE :name");
            countSql.append(" AND name LIKE :name");
            params.put("name", "%" + name + "%");
        }

        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE :email");
            countSql.append(" AND email LIKE :email");
            params.put("email", "%" + email + "%");
        }

        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND gender LIKE :gender");
            countSql.append(" AND gender LIKE :gender");
            params.put("gender", "%" + gender + "%");
        }

        if (ageMin != null && ageMax != null) {
            sql.append(" AND age >= :ageMin and age <= :ageMax");
            countSql.append(" AND age >= :ageMin and age <= :ageMax");
            params.put("ageMin", ageMin);
        }

        int offset = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();

        sql.append(" ORDER BY id ASC LIMIT :limit OFFSET :offset");

        Query query = entityManager.createNativeQuery(sql.toString());

        params.forEach(query::setParameter);
        query.setParameter("limit", pageSize);
        query.setParameter("offset", offset);

        List<Object[]> resultList = query.getResultList();

        List<Map<String, Object>> content = resultList.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", row[0]);
            map.put("name", row[1]);
            map.put("age", row[2]);
            map.put("email", row[3]);
            map.put("phone", row[4]);
            map.put("gender", row[5]);
            return map;
        }).toList();

        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        params.forEach(countQuery::setParameter);

        Long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(content, pageable, total);
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
