package com.example.session_4.repository;

import com.example.session_4.model.CourseStatus;
import com.example.session_4.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    // Kiểm tra khóa học có tồn tại và đang ở trạng thái cụ thể không
    boolean existsByIdAndStatus(Long id, CourseStatus status);
}
