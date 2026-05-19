package com.example.session_4.controller;

import com.example.session_4.model.dto.request.InstructorCreateRequest;
import com.example.session_4.model.dto.response.ApiData;
import com.example.session_4.model.dto.response.InstructorResponse;
import com.example.session_4.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiData<List<InstructorResponse>>> getAllInstructors() {
        List<InstructorResponse> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(
                new ApiData<>(true, "Get all instructors successfully", instructors, HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiData<InstructorResponse>> getInstructorById(@PathVariable Long id) {
        InstructorResponse instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(
                new ApiData<>(true, "Get instructor successfully", instructor, HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiData<InstructorResponse>> createInstructor(@Valid @RequestBody InstructorCreateRequest request) {
        InstructorResponse instructor = instructorService.createInstructor(request);
        return new ResponseEntity<>(
                new ApiData<>(true, "Create instructor successfully", instructor, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiData<InstructorResponse>> updateInstructor(@PathVariable Long id,
                                                                        @Valid @RequestBody InstructorCreateRequest request) {
        InstructorResponse instructor = instructorService.updateInstructor(id, request);
        return ResponseEntity.ok(
                new ApiData<>(true, "Update instructor successfully", instructor, HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
