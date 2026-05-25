package com.example.session_4.controller;

import com.example.session_4.model.CourseStatus;
import com.example.session_4.model.dto.request.CourseEnrollmentRequest;
import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.ApiResponse;
import com.example.session_4.model.dto.response.CourseEnrollmentResponse;
import com.example.session_4.model.dto.response.CourseResponse;
import com.example.session_4.model.dto.response.CourseResponseV2;
import com.example.session_4.model.dto.response.PageResponse;
import com.example.session_4.service.CourseService;
import com.example.session_4.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    // ===== Course CRUD =====

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseV2>>> getAllCourses(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) Sort.Direction direction,
            @RequestParam(value = "status", required = false) CourseStatus status,
            @RequestParam(value = "keyword", required = false) String keyword) {
        PageResponse<CourseResponseV2> courses = courseService.getPagedCourses(page, size, sortBy, direction, status, keyword);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get all courses successfully", courses, HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get course successfully", courseService.getCourseById(id), HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse course = courseService.createCourse(request);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Create course successfully", course, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable Long id,
                                                                 @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Update course successfully", courseService.updateCourse(id, request), HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    //— Thêm sinh viên vào khóa học
    @PostMapping("/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> enrollStudent(
            @PathVariable Long courseId,
            @RequestBody CourseEnrollmentRequest request) {
        CourseEnrollmentResponse enrollment = enrollmentService.enrollStudent(courseId, request);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Enroll student successfully", enrollment, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }
// — Xóa sinh viên khỏi khóa học
    @DeleteMapping("/{courseId}/enrollments/students/{studentId}")
    public ResponseEntity<ApiResponse<Void>> removeStudentFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        enrollmentService.removeStudentFromCourse(courseId, studentId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Remove student from course successfully", null, HttpStatus.NO_CONTENT),
                HttpStatus.NO_CONTENT
        );
    }
    // — Tìm kiếm sinh viên trong khóa học
    @GetMapping("/{courseId}/enrollments/students")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponse>>> searchStudentsInCourse(
            @PathVariable Long courseId,
            @RequestParam(required = false) String search) {
        List<CourseEnrollmentResponse> enrollments = enrollmentService.searchStudentsInCourse(courseId, search);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Search students in course successfully", enrollments, HttpStatus.OK)
        );
    }
}
