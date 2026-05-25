package com.example.session_4.controller;

import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.ApiResponse;
import com.example.session_4.model.dto.response.PageResponse;
import com.example.session_4.model.dto.response.StudentResponse;
import com.example.session_4.service.StudentService;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> getAllStudents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) Sort.Direction direction,
            @RequestParam(value = "keyword", required = false) String keyword) {
        PageResponse<StudentResponse> students = studentService.getPagedStudents(page, size, sortBy, direction, keyword);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get all students successfully", students, HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get student successfully", studentService.getStudentById(id), HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.createStudent(request);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Create student successfully", student, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable Long id,
                                                                    @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Update student successfully", studentService.updateStudent(id, request), HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
