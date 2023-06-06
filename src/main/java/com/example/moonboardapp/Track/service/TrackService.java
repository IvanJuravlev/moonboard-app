package com.example.moonboardapp.Track.service;

import com.example.moonboardapp.Track.Dto.NewTrackDto;
import com.example.moonboardapp.Track.Dto.TrackUpdate;
import com.example.moonboardapp.Track.Grades.Grade;
import com.example.moonboardapp.Track.Grades.GradeRepository;
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
    private final GradeRepository gradeRepository;

    @Transactional
    public Track createTrack(long userId, NewTrackDto newTrackDto) {
        /**Сделать маппер для сущности TrackCreate.*/

        Grade grade = gradeRepository.findById(newTrackDto.getGrade()).orElseThrow();
        Track track = Track.builder().name(newTrackDto.getName()).
                creatorId(userId).
                trackNumberField(newTrackDto.getTrackNumberField()).
                description(newTrackDto.getDescription()).
                videoUrl(newTrackDto.getVideoUrl()).
                grade(grade).
                publishedDate(Timestamp.valueOf(LocalDateTime.now())).build();

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
        /**Добавить Pageble */
        return trackRepository.findAll();
    }

    public void deleteTrackById(long userId, long trackId){
        /**Добавить проверку на пользователя*/
        getTrackById(trackId);

        trackRepository.deleteById(trackId);
    }

}
