package com.example.session_4.service.impl;

import com.example.session_4.mapper.StudentMapper;
import com.example.session_4.model.dto.request.StudentRequest;
import com.example.session_4.model.dto.response.StudentResponse;
import com.example.session_4.model.entity.Student;
import com.example.session_4.repository.IStudentRepository;
import com.example.session_4.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentImpl implements StudentService {
    private final IStudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentImpl(IStudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentMapper.toDtoList(studentRepository.findAll());
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
        return studentMapper.toDto(student);
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = studentMapper.toEntity(request);
        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
        studentMapper.updateEntity(request, student);
        Student saved = studentRepository.save(student);
        return studentMapper.toDto(saved);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
