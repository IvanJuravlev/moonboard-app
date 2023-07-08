package com.moonboardapp.trackingProblems.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingProblemDto {
    long id;
    long problemId;
    long userId;
    boolean climbed;
    Long attempts;
    double rating;
    String videoUrl;
    LocalDateTime finishingTime;
}


