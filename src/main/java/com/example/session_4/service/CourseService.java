package com.example.session_4.service;

import com.example.session_4.model.CourseStatus;
import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.CourseResponse;
import com.example.session_4.model.dto.response.CourseResponseV2;
import com.example.session_4.model.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses();
    PageResponse<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction);
    PageResponse<CourseResponseV2> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction, CourseStatus status, String keyword);
    CourseResponse getCourseById(Long id);
    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
}
