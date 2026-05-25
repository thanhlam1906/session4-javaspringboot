package com.example.session_4.repository;

import com.example.session_4.model.dto.response.StudentResponse;
import com.example.session_4.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IStudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new com.example.session_4.model.dto.response.StudentResponse(s.id, s.name, s.email) " +
           "FROM Student s " +
           "WHERE (:keyword IS NULL OR TRIM(:keyword) = '' " +
           "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<StudentResponse> searchStudents(@Param("keyword") String keyword, Pageable pageable);
}

