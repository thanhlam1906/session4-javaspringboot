package com.example.session_4.repository;

import com.example.session_4.model.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInstructorRepository extends JpaRepository<Instructor, Long> {
}
