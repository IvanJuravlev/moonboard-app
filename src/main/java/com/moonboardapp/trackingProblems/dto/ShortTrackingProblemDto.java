package com.moonboardapp.trackingProblems.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShortTrackingProblemDto {
    @NotNull
    long problem;
    @NotNull
    long user;
    boolean climbed;
    Long attempts;
    String videoUrl;
}
