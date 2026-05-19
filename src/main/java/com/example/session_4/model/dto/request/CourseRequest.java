package com.example.session_4.model.dto.request;

import com.example.session_4.model.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRequest {
    private String title;
    private CourseStatus status;
    private Long instructorId;
}
