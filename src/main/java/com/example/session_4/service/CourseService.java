package com.example.session_4.service;

import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
}
