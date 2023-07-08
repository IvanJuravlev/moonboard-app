package com.moonboardapp.trackingProblems.service;


import com.moonboardapp.exception.ForbiddenException;
import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import com.moonboardapp.problem.service.ProblemService;
import com.moonboardapp.trackingProblems.dto.ShortTrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.UpdateTrackingProblemDto;
import com.moonboardapp.trackingProblems.mapper.TrackingProblemMapper;
import com.moonboardapp.trackingProblems.model.TrackingProblem;
import com.moonboardapp.trackingProblems.repository.TrackingProblemRepository;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackingProblemService {
    private final TrackingProblemRepository repository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final ProblemService problemService;



    @Transactional
    public TrackingProblemDto create(ShortTrackingProblemDto shortTrackingProblemDto) {
        checkUser(shortTrackingProblemDto.getUserId());
        Problem problem = checkProblem(shortTrackingProblemDto.getProblemId());
        TrackingProblem trackingProblem = TrackingProblemMapper.TRACKING_PROBLEM_MAPPER
                .toTrackingProblemFromShortDto(shortTrackingProblemDto);
        if (trackingProblem.isClimbed()) {
            trackingProblem.setFinishingTime(LocalDateTime.now());
        }
        if (shortTrackingProblemDto.getRating() != null && shortTrackingProblemDto.isClimbed()) {
            problemService.addRating(problem.getProblemId(), shortTrackingProblemDto.getRating());
        }
        repository.save(trackingProblem);
        log.info("User created with id {}", trackingProblem.getId());
        return TrackingProblemMapper.TRACKING_PROBLEM_MAPPER.toTrackingProblemDto(trackingProblem);
    }

    @Transactional
    public TrackingProblemDto update(long trackingProblemId,
                                     UpdateTrackingProblemDto updateTrackingProblemDto) {
        checkUser(updateTrackingProblemDto.getUserId());
        Problem problem = checkProblem(updateTrackingProblemDto.getProblemId());
        TrackingProblem trackingProblem = checkTrackingProblem(trackingProblemId);
        if (updateTrackingProblemDto.isClimbed()) {
            trackingProblem.setClimbed(updateTrackingProblemDto.isClimbed());
            trackingProblem.setFinishingTime(LocalDateTime.now());
        }
        if (updateTrackingProblemDto.getRating() != null) {
            problemService.addRating(problem.getProblemId(), updateTrackingProblemDto.getRating());
        }
        if (updateTrackingProblemDto.getAttempts() != null) {
            trackingProblem.setAttempts(updateTrackingProblemDto.getAttempts());
        }
        if (!updateTrackingProblemDto.getVideoUrl().isEmpty()) {
            trackingProblem.setVideoUrl(updateTrackingProblemDto.getVideoUrl());
        }
        return TrackingProblemMapper.TRACKING_PROBLEM_MAPPER.toTrackingProblemDto(trackingProblem);
    }

    public TrackingProblemDto getById(long trackingProblemId) {
        TrackingProblem trackingProblem = checkTrackingProblem(trackingProblemId);
        return TrackingProblemMapper.TRACKING_PROBLEM_MAPPER.toTrackingProblemDto(trackingProblem);
    }

    public List<TrackingProblemDto> getByUserId(long userId, Pageable pageable) {
        checkUser(userId);
        List<TrackingProblemDto> trackingProblemDtoList = repository.findByUserUserId(userId, pageable).stream()
                .map(TrackingProblemMapper.TRACKING_PROBLEM_MAPPER::toTrackingProblemDto)
                .collect(Collectors.toList());
        return trackingProblemDtoList;
    }

    @Transactional
    public void deleteById(long userId, long trackingProblemId) {
        checkUser(userId);
        TrackingProblem trackingProblem = checkTrackingProblem(trackingProblemId);
        if (trackingProblem.getUser().getUserId() == userId) {
            repository.deleteById(trackingProblemId);
        } else throw new ForbiddenException("Only owner can delete tracking problem");
        log.info("TrackingProblem with id {} deleted", trackingProblemId);
    }

    public User checkUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
    }

    public Problem checkProblem(long problemId) {
        return problemRepository.findById(problemId).orElseThrow(() ->
                new NotFoundException(String.format("Problem with id %d not found", problemId)));
    }

    public TrackingProblem checkTrackingProblem(long trackingProblemId) {
        return repository.findById(trackingProblemId).orElseThrow(() ->
                new NotFoundException(String.format("TrackingProblem with id %d not found", trackingProblemId)));
    }

}


