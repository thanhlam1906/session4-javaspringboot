package com.example.session_4.service;

import com.example.session_4.model.dto.request.InstructorCreateRequest;
import com.example.session_4.model.dto.response.InstructorResponse;

import java.util.List;

public interface InstructorService {
    List<InstructorResponse> getAllInstructors();
    InstructorResponse getInstructorById(Long id);
    InstructorResponse createInstructor(InstructorCreateRequest request);
    InstructorResponse updateInstructor(Long id, InstructorCreateRequest request);
    void deleteInstructor(Long id);
}
