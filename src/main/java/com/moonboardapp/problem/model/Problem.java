package com.moonboardapp.problem.model;

import com.moonboardapp.problem.grades.Grade;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "problem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
    public class Problem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long problemId;
        long creatorId;
        String name;
        String description;
        @ManyToOne
        @JoinColumn(name = "grade")
        Grade grade;
        double rating;
        @ElementCollection
        @CollectionTable(name = "problem_hooks", joinColumns = @JoinColumn(name = "problem_id"))
        List<String> problemNumberField;
        String videoUrl;
        int climbs;
        String averageGrade;
        Timestamp publishedDate;
    }
