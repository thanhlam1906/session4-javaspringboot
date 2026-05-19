package com.example.session_4.service;

import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    StudentResponse getStudentById(Long id);
    StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
}
