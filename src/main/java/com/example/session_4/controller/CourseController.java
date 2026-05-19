package com.example.session_4.controller;

import com.example.session_4.model.dto.request.CourseEnrollmentRequest;
import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.ApiData;
import com.example.session_4.model.dto.response.CourseEnrollmentResponse;
import com.example.session_4.model.dto.response.CourseResponse;
import com.example.session_4.service.CourseService;
import com.example.session_4.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ApiData<List<CourseResponse>>> getAllCourses() {
        return ResponseEntity.ok(
                new ApiData<>(true, "Get all courses successfully", courseService.getAllCourses(), HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiData<CourseResponse>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiData<>(true, "Get course successfully", courseService.getCourseById(id), HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiData<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse course = courseService.createCourse(request);
        return new ResponseEntity<>(
                new ApiData<>(true, "Create course successfully", course, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiData<CourseResponse>> updateCourse(@PathVariable Long id,
                                                                 @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(
                new ApiData<>(true, "Update course successfully", courseService.updateCourse(id, request), HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Enrollment sub-resource endpoints =====

    // POST /courses/{courseId}/enrollments — Thêm sinh viên vào khóa học
    @PostMapping("/{courseId}/enrollments")
    public ResponseEntity<ApiData<CourseEnrollmentResponse>> enrollStudent(
            @PathVariable Long courseId,
            @RequestBody CourseEnrollmentRequest request) {
        CourseEnrollmentResponse enrollment = enrollmentService.enrollStudent(courseId, request);
        return new ResponseEntity<>(
                new ApiData<>(true, "Enroll student successfully", enrollment, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    // DELETE /courses/{courseId}/enrollments/students/{studentId} — Xóa sinh viên khỏi khóa học
    @DeleteMapping("/{courseId}/enrollments/students/{studentId}")
    public ResponseEntity<ApiData<Void>> removeStudentFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        enrollmentService.removeStudentFromCourse(courseId, studentId);
        return new ResponseEntity<>(
                new ApiData<>(true, "Remove student from course successfully", null, HttpStatus.NO_CONTENT),
                HttpStatus.NO_CONTENT
        );
    }

    // GET /courses/{courseId}/enrollments/students?search=ten_sv — Tìm kiếm sinh viên trong khóa học
    @GetMapping("/{courseId}/enrollments/students")
    public ResponseEntity<ApiData<List<CourseEnrollmentResponse>>> searchStudentsInCourse(
            @PathVariable Long courseId,
            @RequestParam(required = false) String search) {
        List<CourseEnrollmentResponse> enrollments = enrollmentService.searchStudentsInCourse(courseId, search);
        return ResponseEntity.ok(
                new ApiData<>(true, "Search students in course successfully", enrollments, HttpStatus.OK)
        );
    }
}
