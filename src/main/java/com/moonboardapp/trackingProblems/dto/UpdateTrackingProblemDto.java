package com.moonboardapp.trackingProblems.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTrackingProblemDto {
     long userId;
     long problemId;
     boolean climbed;
     Long attempts;
     String videoUrl;
     Integer rating;
}
