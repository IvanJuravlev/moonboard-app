package com.moonboardapp.track.dto;

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
    List<String> trackNumberField;
    String videoUrl;
}
