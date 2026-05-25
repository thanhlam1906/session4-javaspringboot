package com.example.session_4.repository;

import com.example.session_4.model.CourseStatus;
import com.example.session_4.model.dto.response.CourseResponseV2;
import com.example.session_4.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    boolean existsByIdAndStatus(Long id, CourseStatus status);

    @Query("SELECT c FROM Course c WHERE c.status = :status")
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);

    @Query("SELECT new com.example.session_4.model.dto.response.CourseResponseV2(c.id, c.title, c.status) " +
           "FROM Course c WHERE c.status = :status")
    Page<CourseResponseV2> findAllProjectedByStatus(@Param("status") CourseStatus status, Pageable pageable);

    @Query("SELECT new com.example.session_4.model.dto.response.CourseResponseV2(c.id, c.title, c.status) " +
           "FROM Course c " +
           "WHERE (:status IS NULL OR c.status = :status) " +
           "AND (:keyword IS NULL OR TRIM(:keyword) = '' OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<CourseResponseV2> searchCourses(
            @Param("status") CourseStatus status,
            @Param("keyword") String keyword,
            Pageable pageable);
}

