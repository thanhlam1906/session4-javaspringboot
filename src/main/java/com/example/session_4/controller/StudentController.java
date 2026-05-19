package com.example.session_4.controller;

import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.ApiData;
import com.example.session_4.model.dto.response.StudentResponse;
import com.example.session_4.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ApiData<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(
                new ApiData<>(true, "Get all students successfully", studentService.getAllStudents(), HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiData<StudentResponse>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiData<>(true, "Get student successfully", studentService.getStudentById(id), HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiData<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.createStudent(request);
        return new ResponseEntity<>(
                new ApiData<>(true, "Create student successfully", student, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiData<StudentResponse>> updateStudent(@PathVariable Long id,
                                                                    @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(
                new ApiData<>(true, "Update student successfully", studentService.updateStudent(id, request), HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
