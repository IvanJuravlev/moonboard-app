package com.moonboardapp.problem.dto;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProblemDto {
    long id;
    @NotNull
    long creatorId;
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    long grade;
    @NotNull
    List<String> problemNumberField;
    String videoUrl;
    int climbs;
    int rating;
    String averageGrade;
    @NotNull
    Timestamp publishedDate;
}
