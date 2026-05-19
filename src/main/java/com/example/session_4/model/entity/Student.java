package com.example.session_4.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Student {
    @Id
    @Column(name = "student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "student_name", nullable = false)
    private String name;
    @Column(name = "student_email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "student")
    private List<StudentEnrollment> enrollments;
}
