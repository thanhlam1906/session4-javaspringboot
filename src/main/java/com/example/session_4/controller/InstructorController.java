package com.example.session_4.controller;

import com.example.session_4.model.dto.request.InstructorCreateRequest;
import com.example.session_4.model.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getAllInstructors() {
        List<InstructorResponse> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get all instructors successfully", instructors, HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> getInstructorById(@PathVariable Long id) {
        InstructorResponse instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Get instructor successfully", instructor, HttpStatus.OK)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InstructorResponse>> createInstructor(@Valid @RequestBody InstructorCreateRequest request) {
        InstructorResponse instructor = instructorService.createInstructor(request);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Create instructor successfully", instructor, HttpStatus.CREATED),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(@PathVariable Long id,
                                                                        @Valid @RequestBody InstructorCreateRequest request) {
        InstructorResponse instructor = instructorService.updateInstructor(id, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Update instructor successfully", instructor, HttpStatus.OK)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
