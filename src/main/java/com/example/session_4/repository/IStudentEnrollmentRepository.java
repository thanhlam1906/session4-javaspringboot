package com.example.session_4.repository;

import com.example.session_4.model.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IStudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    List<StudentEnrollment> findByCourseId(Long courseId);

    List<StudentEnrollment> findByStudentId(Long studentId);

    // Kiểm tra sinh viên đã đăng ký khóa học chưa
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);

    // Tìm enrollment theo courseId và studentId để xóa
    Optional<StudentEnrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

    // Tìm kiếm sinh viên trong khóa học theo tên (LIKE, case-insensitive)
    @Query("SELECT se FROM StudentEnrollment se " +
           "WHERE se.course.id = :courseId " +
           "AND LOWER(se.student.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<StudentEnrollment> searchStudentsInCourse(@Param("courseId") Long courseId,
                                                    @Param("search") String search);
}
