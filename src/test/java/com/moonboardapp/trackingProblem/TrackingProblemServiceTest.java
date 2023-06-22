package com.moonboardapp.trackingProblem;

import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import com.moonboardapp.trackingProblems.dto.ShortTrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.UpdateTrackingProblemDto;
import com.moonboardapp.trackingProblems.mapper.TrackingProblemMapper;
import com.moonboardapp.trackingProblems.model.TrackingProblem;
import com.moonboardapp.trackingProblems.repository.TrackingProblemRepository;
import com.moonboardapp.trackingProblems.service.TrackingProblemService;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TrackingProblemServiceTest {

    @InjectMocks
    TrackingProblemService service;
    @Mock
    TrackingProblemRepository repository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProblemRepository problemRepository;


    private TrackingProblem trackingProblem;

    private User user;

    private Problem problem;
    private UpdateTrackingProblemDto updateTrackingProblemDto;

    @BeforeEach
    void beforeEach() {
        Timestamp publishedDate = Timestamp.valueOf("2023-06-19 12:34:56");
        List<String> hooksList = List.of("somth");

        Grade grade = new Grade(1L, "6b");
        user = new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 5L);
        userRepository.save(user);
        problem = new Problem(1L, 1L, "SomeName", "SomeDisc",
                grade, 5, hooksList, "videoUrl", 4, "6b", publishedDate);
        problemRepository.save(problem);
        trackingProblem = new TrackingProblem(1L, problem, user, false, 4L, "videoUrl",
                        LocalDateTime.now());
        updateTrackingProblemDto = new UpdateTrackingProblemDto(1L, 1L,
                true, 6L, "SomeOtherVideo");
    }

    @Test
    void createTrackingProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(repository.save(any(TrackingProblem.class))).thenReturn(trackingProblem);

        TrackingProblemDto trackingProblemDto =
                service.create(TrackingProblemMapper.TRACKING_PROBLEM_MAPPER.toShortTrackingProblemDto(trackingProblem));
        trackingProblemDto.setId(1L);

        assertEquals(1L, trackingProblemDto.getId());
        assertEquals(1L, trackingProblemDto.getProblemId());
        assertEquals(1L, trackingProblemDto.getUserId());
        assertEquals(false, trackingProblemDto.isClimbed());
        assertEquals(4L, trackingProblemDto.getAttempts());
        assertEquals("videoUrl", trackingProblemDto.getVideoUrl());
    }

    @Test
    void updateTrackingProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(trackingProblem));

        TrackingProblemDto trackingProblemDto =
                service.update(1L, updateTrackingProblemDto);

        assertEquals(1L, trackingProblemDto.getId());
        assertEquals(1L, trackingProblemDto.getProblemId());
        assertEquals(1L, trackingProblemDto.getUserId());
        assertEquals(true, trackingProblemDto.isClimbed());
        assertEquals(6L, trackingProblemDto.getAttempts());
        assertEquals("SomeOtherVideo", trackingProblemDto.getVideoUrl());
    }

    @Test
    void updateTrackingProblemWithWrongUserTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(trackingProblem));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                service.update(1L, updateTrackingProblemDto));

        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void createTrackingProblemWithWrongProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.empty());
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(trackingProblem));

        ShortTrackingProblemDto shortTrackingProblemDto = TrackingProblemMapper
                .TRACKING_PROBLEM_MAPPER.toShortTrackingProblemDto(trackingProblem);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                service.create(shortTrackingProblemDto));

        assertEquals("Problem with id 1 not found", exception.getMessage());
    }

    @Test
    void getTrackingProblemByUserIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.findByUserUserId(anyLong(), any(PageRequest.class))).thenReturn(List.of(trackingProblem));

        PageRequest pageable = PageRequest.of(1 / 10, 10);

        List<TrackingProblemDto> trackingProblemDtoList = service.getByUserId(1L, pageable);

        assertEquals(1L, trackingProblemDtoList.get(0).getId());
        assertEquals(1L, trackingProblemDtoList.get(0).getProblemId());
        assertEquals(1L, trackingProblemDtoList.get(0).getUserId());
        assertEquals(false, trackingProblemDtoList.get(0).isClimbed());
        assertEquals(4L, trackingProblemDtoList.get(0).getAttempts());
        assertEquals("videoUrl", trackingProblemDtoList.get(0).getVideoUrl());
    }

    @Test
    void deleteTrackingProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(trackingProblem));

        service.deleteById(1L, 1L);

        verify(repository, times(1)).findById(trackingProblem.getId());
        verify(repository, times(1)).deleteById(trackingProblem.getId());

        assertFalse(repository.existsById(trackingProblem.getId()));
    }


}
