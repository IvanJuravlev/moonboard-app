package com.moonboardapp.problem.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewProblemDto {
    long creatorId;
    String name;
    String description;
    long grade;
    List<String> problemNumberField;
    String videoUrl;
}
