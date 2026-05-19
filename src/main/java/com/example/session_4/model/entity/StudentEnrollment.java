package com.example.session_4.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "studentEnrollment")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudentEnrollment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private LocalDateTime enrolledAt;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    void onCreate() {
        this.enrolledAt = LocalDateTime.now();
    }
}