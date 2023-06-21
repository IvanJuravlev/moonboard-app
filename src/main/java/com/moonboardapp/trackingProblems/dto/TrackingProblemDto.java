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
    long problem;
    long user;
    boolean climbed;
    Long attempts;
    String videoUrl;
    LocalDateTime finishingTime;
}
