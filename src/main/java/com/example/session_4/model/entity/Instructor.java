package com.example.session_4.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "instructor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long id;
    @Column(name = "instructor_name", nullable = false)
    private String name;
    @Column(name = "instructor_email",nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "instructor")
    List<Course> course;
}
