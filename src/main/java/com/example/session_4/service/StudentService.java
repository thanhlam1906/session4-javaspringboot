package com.example.session_4.service;

import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.PageResponse;
import com.example.session_4.model.dto.response.StudentResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAllStudents();
    PageResponse<StudentResponse> getPagedStudents(int page, int size, String sortBy, Sort.Direction direction, String keyword);
    StudentResponse getStudentById(Long id);
    StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
}

