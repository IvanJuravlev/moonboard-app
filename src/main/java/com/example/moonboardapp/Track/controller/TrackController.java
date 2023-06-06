package com.example.moonboardapp.Track.controller;

import com.example.moonboardapp.Track.Dto.NewTrackDto;
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
    public Track createTrack(@RequestHeader long userId,
                            @RequestBody NewTrackDto track) {
       return trackService.createTrack(userId, track);
    }

    @PatchMapping("{trackId}")
    public Track updateTrack(@RequestHeader long userId,
                            @PathVariable long trackId,
                            @RequestBody TrackUpdate track) {
       return trackService.updateTrack(userId, trackId, track);
    }

    @GetMapping("{id}")
    public Track getTrackById(@PathVariable long id) {
        return trackService.getTrackById(id);
    }

    @GetMapping("/user")
    public List<Track> getTracksByUserId(@RequestHeader long userId){
        return trackService.getTracksByUserId(userId);
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @DeleteMapping("{trackId}")
    public void deleteTrackById(@RequestHeader long userId,
                                @PathVariable long trackId){
        trackService.deleteTrackById(userId, trackId);
    }

}
