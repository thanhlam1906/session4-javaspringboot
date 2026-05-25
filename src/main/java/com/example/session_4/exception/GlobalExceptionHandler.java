package com.example.session_4.exception;

import com.example.session_4.model.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Resource không tồn tại
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

    // 400 - Validation lỗi (ví dụ: @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                new ApiResponse<>(false, errors, null, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    // 400 - Lỗi tham số không hợp lệ (Type mismatch)
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, "Invalid parameter: " + ex.getName(), null, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    // 400 - Lỗi tham số không hợp lệ (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST
        );
    }

    // 409 - Conflict / Vi phạm business rule (ví dụ: đăng ký trùng, khóa học không ACTIVE)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(IllegalStateException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    // 500 - Lỗi không xác định
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntime(RuntimeException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
