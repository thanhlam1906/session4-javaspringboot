package com.example.session_4.mapper;

import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.CourseResponse;
import com.example.session_4.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    // entity->response
    CourseResponse toDto(Course course);
    // request->entity
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseRequest courseRequest);
    List<CourseResponse> toDtoList(List<Course> courses);

}
