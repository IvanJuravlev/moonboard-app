package com.example.moonboardapp.Track.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackUpdate {
    String name;
    String description;
    String videoUrl;
}
