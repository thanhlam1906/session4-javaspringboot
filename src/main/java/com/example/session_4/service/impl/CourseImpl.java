package com.example.session_4.service.impl;

import com.example.session_4.mapper.CourseMapper;
import com.example.session_4.model.dto.request.CourseRequest;
import com.example.session_4.model.dto.response.CourseResponse;
import com.example.session_4.model.entity.Course;
import com.example.session_4.model.entity.Instructor;
import com.example.session_4.repository.ICourseRepository;
import com.example.session_4.repository.IInstructorRepository;
import com.example.session_4.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseImpl implements CourseService {
    private final ICourseRepository courseRepository;
    private final IInstructorRepository instructorRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseImpl(ICourseRepository courseRepository, IInstructorRepository instructorRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));
        return courseMapper.toDto(course);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Course course = courseMapper.toEntity(request);
        if (request.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new NoSuchElementException("Instructor not found with id: " + request.getInstructorId()));
            course.setInstructor(instructor);
        }
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));
        course.setTitle(request.getTitle());
        course.setStatus(request.getStatus());
        if (request.getInstructorId() != null) {
            Instructor instructor = instructorRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new NoSuchElementException("Instructor not found with id: " + request.getInstructorId()));
            course.setInstructor(instructor);
        }
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new NoSuchElementException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
