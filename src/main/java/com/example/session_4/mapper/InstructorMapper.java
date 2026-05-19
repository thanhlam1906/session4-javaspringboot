package com.example.session_4.mapper;

import com.example.session_4.model.dto.request.InstructorCreateRequest;
import com.example.session_4.model.dto.response.InstructorResponse;
import com.example.session_4.model.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    // entity -> response
    InstructorResponse toDto(Instructor instructor);

    List<InstructorResponse> toDtoList(List<Instructor> instructors);

    // request -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    Instructor toEntity(InstructorCreateRequest request);

    // update entity from request
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    void updateEntity(InstructorCreateRequest request, @MappingTarget Instructor instructor);
}
