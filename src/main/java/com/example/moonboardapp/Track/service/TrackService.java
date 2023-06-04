package com.example.moonboardapp.Track.service;

import com.example.moonboardapp.Track.Dto.TrackUpdate;
import com.example.moonboardapp.Track.exception.NotFoundException;
import com.example.moonboardapp.Track.model.Track;
import com.example.moonboardapp.Track.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    @Transactional
    public Track createTrack(long userId, Track track) {
        /**Сделать маппер для сущности TrackCreate. Подумать о добавлении даты создания трассы */
        track.setCreatorId(userId);

        return trackRepository.save(track);
    }

    @Transactional
    public Track updateTrack(long userId, long trackId, TrackUpdate changedTrack) {
        /**Добавить проверку на пользователя обновляющего трассу*/

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

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public void deleteTrackById(long userId, long trackId){
        /**Добавить проверку на пользователя обновляющего трассу*/
        getTrackById(trackId);

        trackRepository.deleteById(trackId);
    }

}
