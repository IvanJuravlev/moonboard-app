package com.moonboardapp.track.repository;

import com.moonboardapp.track.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByCreatorId(long creatorId);
}
