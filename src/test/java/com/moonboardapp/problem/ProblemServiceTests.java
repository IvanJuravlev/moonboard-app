package com.moonboardapp.problem;

import com.moonboardapp.exception.ForbiddenException;
import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.grades.GradeRepository;
import com.moonboardapp.problem.mapper.ProblemMapper;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import com.moonboardapp.problem.service.ProblemService;
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
public class ProblemServiceTests {
    @InjectMocks
    ProblemService service;
    @Mock
    UserRepository userRepository;
    @Mock
    ProblemRepository problemRepository;
    @Mock
    TrackingProblemRepository trackingProblemRepository;
    @Mock
    TrackingProblemService trackingProblemService;
    @Mock
    GradeRepository gradeRepository;


    private Problem problem;
    private User user;
    private TrackingProblem trackingProblem;
    private TrackingProblem trackingProblem2;
    private Grade grade;
    private List<String> hooksList;

    private Timestamp publishedDate;
    private ProblemUpdateDto problemUpdateDto;

    PageRequest pageable;


    @BeforeEach
    void beforeEach() {
        publishedDate = Timestamp.valueOf("2023-06-19 12:34:56");
        hooksList = List.of("somth");

        pageable = PageRequest.of(1 / 10, 10);

        grade = new Grade(1L, "6b");
        user = new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 0L);
        userRepository.save(user);
        problem = new Problem(1L, 1L, "SomeName", "SomeDisc",
                grade, 0, hooksList, "videoUrl", 4, "6b", publishedDate);
        trackingProblem = new TrackingProblem(1L, problem, user, false,4L, 5L, "videoUrl",
                LocalDateTime.now());
        trackingProblem = new TrackingProblem(2L, problem, user, false,4L, 4L, "videoUrl",
                LocalDateTime.now());
        trackingProblemRepository.save(trackingProblem2);
        problemUpdateDto = new ProblemUpdateDto("SomeNewName", "SomeNewDesc", "SomeNewViceoUrl");
    }

    @Test
    void createProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);


        ProblemDto problemDto = service.createProblem(user.getUserId(),
                ProblemMapper.PROBLEM_MAPPER.toProblemDto(problem));

        assertEquals(1L, problemDto.getProblemId());
        assertEquals(1L, problemDto.getCreatorId());
        assertEquals("SomeName", problemDto.getName());
        assertEquals("SomeDisc", problemDto.getDescription());
        assertEquals(1, problemDto.getGrade());
        assertEquals(0, problemDto.getRating());
        assertEquals(hooksList, problemDto.getProblemNumberField());
        assertEquals("videoUrl", problemDto.getVideoUrl());
        assertEquals(4, problemDto.getClimbs());
        assertEquals("6b", problemDto.getAverageGrade());
        assertEquals(publishedDate, problemDto.getPublishedDate());
    }

    @Test
    void updateProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        ProblemDto problemDto = service.updateProblem(1L, 1L, problemUpdateDto);

        assertEquals(1L, problemDto.getProblemId());
        assertEquals(1L, problemDto.getCreatorId());
        assertEquals("SomeNewName", problemDto.getName());
        assertEquals("SomeNewDesc", problemDto.getDescription());
        assertEquals(1, problemDto.getGrade());
        assertEquals(0, problemDto.getRating());
        assertEquals(hooksList, problemDto.getProblemNumberField());
        assertEquals("SomeNewViceoUrl", problemDto.getVideoUrl());
        assertEquals(4, problemDto.getClimbs());
        assertEquals("6b", problemDto.getAverageGrade());
        assertEquals(publishedDate, problemDto.getPublishedDate());
    }

    @Test
    void updateProblemByAdminTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        ProblemDto problemDto = service.updateProblemByAdmin(1L, problemUpdateDto);

        assertEquals(1L, problemDto.getProblemId());
        assertEquals(1L, problemDto.getCreatorId());
        assertEquals("SomeNewName", problemDto.getName());
        assertEquals("SomeNewDesc", problemDto.getDescription());
        assertEquals(1, problemDto.getGrade());
        assertEquals(0, problemDto.getRating());
        assertEquals(hooksList, problemDto.getProblemNumberField());
        assertEquals("SomeNewViceoUrl", problemDto.getVideoUrl());
        assertEquals(4, problemDto.getClimbs());
        assertEquals("6b", problemDto.getAverageGrade());
        assertEquals(publishedDate, problemDto.getPublishedDate());
    }

    @Test
    void getProblemsByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findByCreatorId(anyLong(), any(PageRequest.class))).thenReturn(List.of(problem));

        List<ProblemDto> problemDtoList = service.getProblemsByUserId(user.getUserId(), pageable);


        assertEquals(1L, problemDtoList.get(0).getProblemId());
        assertEquals(1L, problemDtoList.get(0).getCreatorId());
        assertEquals("SomeName", problemDtoList.get(0).getName());
        assertEquals("SomeDisc", problemDtoList.get(0).getDescription());
        assertEquals(1, problemDtoList.get(0).getGrade());
        assertEquals(0, problemDtoList.get(0).getRating());
        assertEquals(hooksList, problemDtoList.get(0).getProblemNumberField());
        assertEquals("videoUrl", problemDtoList.get(0).getVideoUrl());
        assertEquals(4, problemDtoList.get(0).getClimbs());
        assertEquals("6b", problemDtoList.get(0).getAverageGrade());
        assertEquals(publishedDate, problemDtoList.get(0).getPublishedDate());
    }



    @Test
    void updateProblemWithWrongUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                service.updateProblem(2L, 1L, problemUpdateDto));

        assertEquals("User with id 2 not found", exception.getMessage());
    }

    @Test
    void updateProblemWithWrongOwnerId() {
        problem.setCreatorId(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        ForbiddenException exception = assertThrows(ForbiddenException.class, () ->
                service.updateProblem(1L, 1L, problemUpdateDto));

        assertEquals("Only owner can change problem", exception.getMessage());
    }

    @Test
    void updateProblemWithWrongProblemId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                service.updateProblem(1L, 2L, problemUpdateDto));

        assertEquals("Problem with id 2 not found", exception.getMessage());
    }

    @Test
    void deleteProblemTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        service.deleteProblemById(1L, 1L);

        verify(problemRepository, times(1)).findById(problem.getProblemId());
        verify(problemRepository, times(1)).deleteById(problem.getProblemId());

        assertFalse(problemRepository.existsById(problem.getProblemId()));
    }

    @Test
    void deleteProblemByAdminTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        service.deleteProblemByAdmin(1L);

        verify(problemRepository, times(1)).findById(problem.getProblemId());
        verify(problemRepository, times(1)).deleteById(problem.getProblemId());

        assertFalse(problemRepository.existsById(problem.getProblemId()));
    }

    @Test
    void deleteProblemWithWrongOwnerTest() {
        problem.setCreatorId(2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));

        ForbiddenException exception = assertThrows(ForbiddenException.class, () ->
                service.deleteProblemById(1L, 1L));

        assertEquals("Only owner can delete problem", exception.getMessage());
    }
}
