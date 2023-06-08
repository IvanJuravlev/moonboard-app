package com.moonboardapp.track.dto;

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
