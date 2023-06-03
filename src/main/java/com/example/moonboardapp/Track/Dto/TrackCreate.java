package com.example.moonboardapp.Track.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackCreate {
    long creatorId;
    String name;
    String description;
    String grade;
    private List<String> trackNumberField;
    String videoUrl;
}
