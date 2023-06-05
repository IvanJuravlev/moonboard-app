package com.example.moonboardapp.Track.service;

import com.example.moonboardapp.Track.Dto.TrackUpdate;
import com.example.moonboardapp.Track.exception.NotFoundException;
import com.example.moonboardapp.Track.model.Track;
import com.example.moonboardapp.Track.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    @Transactional
    public Track createTrack(long userId, Track track) {
        /**Сделать маппер для сущности TrackCreate.*/
        track.setCreatorId(userId);
        track.setPublishedDate(Timestamp.valueOf(LocalDateTime.now()));

        return trackRepository.save(track);
    }

    @Transactional
    public Track updateTrack(long userId, long trackId, TrackUpdate changedTrack) {
        /**Добавить проверку на пользователя обновляющего трассу
         * Добавить поле "Дата изменения. Может быть*/

        Track track = getTrackById(trackId);
        if (!changedTrack.getName().isEmpty()) {
            track.setName(changedTrack.getName());
        }
        if (!changedTrack.getDescription().isEmpty()) {
            track.setDescription(changedTrack.getDescription());
        }
        if (!changedTrack.getVideoUrl().isEmpty()) {
            track.setVideoUrl(changedTrack.getVideoUrl());
        }

        trackRepository.save(track);
        return track;
    }

    public Track getTrackById(long id) {
        return trackRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Трасса с id %d не найдена", id)));
    }

    public List<Track> getTracksByUserId(long userId){
        /**Добавить проверку на пользователя*/

        return trackRepository.findByCreatorId(userId);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public void deleteTrackById(long userId, long trackId){
        /**Добавить проверку на пользователя*/
        getTrackById(trackId);

        trackRepository.deleteById(trackId);
    }

}
