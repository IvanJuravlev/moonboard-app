package com.example.moonboardapp.Track.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "track")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long trackId;
    long creatorId;
    String name;
    String description;
    String grade;
    int rating;
    @ElementCollection
    @CollectionTable(name = "track_hooks", joinColumns = @JoinColumn(name = "trackId"))
    private List<String> trackNumberField;
    String videoUrl;
    int climbs;
    String averageGrade;
}
