package com.example.session_4.service.impl;

import com.example.session_4.mapper.InstructorMapper;
import com.example.session_4.model.dto.request.InstructorCreateRequest;
import com.example.session_4.model.dto.response.InstructorResponse;
import com.example.session_4.model.entity.Instructor;
import com.example.session_4.repository.IInstructorRepository;
import com.example.session_4.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InstructorImpl implements InstructorService {
    private final IInstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    @Autowired
    public InstructorImpl(IInstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return instructorMapper.toDtoList(instructorRepository.findAll());
    }

    @Override
    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with id: " + id));
        return instructorMapper.toDto(instructor);
    }

    @Override
    public InstructorResponse createInstructor(InstructorCreateRequest request) {
        Instructor instructor = instructorMapper.toEntity(request);
        Instructor saved = instructorRepository.save(instructor);
        return instructorMapper.toDto(saved);
    }

    @Override
    public InstructorResponse updateInstructor(Long id, InstructorCreateRequest request) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Instructor not found with id: " + id));
        instructorMapper.updateEntity(request, instructor);
        Instructor saved = instructorRepository.save(instructor);
        return instructorMapper.toDto(saved);
    }

    @Override
    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new NoSuchElementException("Instructor not found with id: " + id);
        }
        instructorRepository.deleteById(id);
    }
}
