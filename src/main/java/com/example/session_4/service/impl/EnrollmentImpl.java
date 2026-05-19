package com.example.session_4.service.impl;

import com.example.session_4.mapper.EnrollmentMapper;
import com.example.session_4.model.CourseStatus;
import com.example.session_4.model.dto.request.CourseEnrollmentRequest;
import com.example.session_4.model.dto.response.CourseEnrollmentResponse;
import com.example.session_4.model.entity.Course;
import com.example.session_4.model.entity.Student;
import com.example.session_4.model.entity.StudentEnrollment;
import com.example.session_4.repository.ICourseRepository;
import com.example.session_4.repository.IStudentEnrollmentRepository;
import com.example.session_4.repository.IStudentRepository;
import com.example.session_4.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnrollmentImpl implements EnrollmentService {
    private final IStudentEnrollmentRepository enrollmentRepository;
    private final IStudentRepository studentRepository;
    private final ICourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Autowired
    public EnrollmentImpl(IStudentEnrollmentRepository enrollmentRepository,
                          IStudentRepository studentRepository,
                          ICourseRepository courseRepository,
                          EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    public CourseEnrollmentResponse enrollStudent(Long courseId, CourseEnrollmentRequest request) {
        // 1. Kiểm tra khóa học tồn tại
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + courseId));

        // 2. Kiểm tra khóa học phải đang ACTIVE
        if (!courseRepository.existsByIdAndStatus(courseId, CourseStatus.ACTIVE)) {
            throw new IllegalStateException("Course with id " + courseId + " is not ACTIVE. Only ACTIVE courses allow enrollment.");
        }

        // 3. Kiểm tra sinh viên tồn tại
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.getStudentId()));

        // 4. Kiểm tra sinh viên đã đăng ký khóa học chưa (mỗi sinh viên chỉ được thêm 1 lần)
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, request.getStudentId())) {
            throw new IllegalStateException("Student with id " + request.getStudentId()
                    + " is already enrolled in course with id " + courseId);
        }

        // 5. Tạo enrollment mới (enrolledAt sẽ tự động set qua @PrePersist)
        StudentEnrollment enrollment = StudentEnrollment.builder()
                .course(course)
                .student(student)
                .build();
        StudentEnrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    @Override
    public void removeStudentFromCourse(Long courseId, Long studentId) {
        // 1. Kiểm tra khóa học tồn tại
        if (!courseRepository.existsById(courseId)) {
            throw new NoSuchElementException("Course not found with id: " + courseId);
        }

        // 2. Kiểm tra sinh viên tồn tại
        if (!studentRepository.existsById(studentId)) {
            throw new NoSuchElementException("Student not found with id: " + studentId);
        }

        // 3. Kiểm tra enrollment tồn tại
        StudentEnrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Student with id " + studentId + " is not enrolled in course with id " + courseId));

        // 4. Xóa enrollment
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<CourseEnrollmentResponse> searchStudentsInCourse(Long courseId, String search) {
        if (!courseRepository.existsById(courseId)) {
            throw new NoSuchElementException("Course not found with id: " + courseId);
        }

        List<StudentEnrollment> enrollments;
        if (search == null || search.isBlank()) {
            enrollments = enrollmentRepository.findByCourseId(courseId);
        } else {
            enrollments = enrollmentRepository.searchStudentsInCourse(courseId, search);
        }
        return enrollmentMapper.toDtoList(enrollments);
    }
}
