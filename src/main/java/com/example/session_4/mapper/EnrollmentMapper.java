package com.example.session_4.mapper;

import com.example.session_4.model.dto.response.CourseEnrollmentResponse;
import com.example.session_4.model.entity.StudentEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    CourseEnrollmentResponse toDto(StudentEnrollment enrollment);

    List<CourseEnrollmentResponse> toDtoList(List<StudentEnrollment> enrollments);
}
