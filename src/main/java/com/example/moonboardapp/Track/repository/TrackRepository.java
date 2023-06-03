package com.example.moonboardapp.Track.repository;

import com.example.moonboardapp.Track.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
