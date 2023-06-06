package com.moonboardapp.problem.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProblemUpdateDto {
    String name;
    String description;
    String videoUrl;
}
