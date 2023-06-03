package com.example.moonboardapp.Track.controller;

import com.example.moonboardapp.Track.Dto.TrackUpdate;
import com.example.moonboardapp.Track.model.Track;
import com.example.moonboardapp.Track.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @PostMapping
    public void createTrack(@RequestHeader long userId,
                            @RequestBody Track track) {
        trackService.createTrack(userId, track);
    }

    @PatchMapping("{trackId}")
    public void updateTrack(@RequestHeader long userId,
                            @PathVariable long trackId,
                            @RequestBody TrackUpdate track) {
        trackService.updateTrack(userId, trackId, track);
    }

    @GetMapping("{id}")
    public Track getTrackById(@PathVariable long id) {
        return trackService.getTrackById(id);
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }
}
