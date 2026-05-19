package com.example.session_4.mapper;

import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.StudentResponse;
import com.example.session_4.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    // entity -> response
    StudentResponse toDto(Student student);

    List<StudentResponse> toDtoList(List<Student> students);

    // request -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    Student toEntity(StudentRequest request);

    // update entity from request
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    void updateEntity(StudentRequest request, @MappingTarget Student student);
}
