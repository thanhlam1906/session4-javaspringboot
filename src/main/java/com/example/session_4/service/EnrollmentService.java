package com.example.session_4.service;

import com.example.session_4.model.dto.request.CourseEnrollmentRequest;
import com.example.session_4.model.dto.response.CourseEnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    // Thêm sinh viên vào khóa học (kiểm tra trùng + khóa học phải ACTIVE)
    CourseEnrollmentResponse enrollStudent(Long courseId, CourseEnrollmentRequest request);

    // Xóa sinh viên khỏi khóa học cụ thể
    void removeStudentFromCourse(Long courseId, Long studentId);

    // Tìm kiếm sinh viên trong khóa học theo tên
    List<CourseEnrollmentResponse> searchStudentsInCourse(Long courseId, String search);
}
