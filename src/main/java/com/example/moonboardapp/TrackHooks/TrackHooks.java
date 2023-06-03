package com.example.moonboardapp.TrackHooks;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "track_hooks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackHooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long trackId;
    String trackNumberField;
}
