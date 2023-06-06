package com.example.moonboardapp.Track.model;

import com.example.moonboardapp.Track.Grades.Grade;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
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
    @ManyToOne
    @JoinColumn(name = "grade")
    Grade grade;
    int rating;
    @ElementCollection
    @CollectionTable(name = "track_hooks", joinColumns = @JoinColumn(name = "trackId"))
    List<String> trackNumberField;
    String videoUrl;
    int climbs;
    String averageGrade;
    Timestamp publishedDate;
}
