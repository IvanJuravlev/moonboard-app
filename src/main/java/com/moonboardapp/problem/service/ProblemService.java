package com.moonboardapp.problem.service;

import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.grades.GradeRepository;
import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.problem.mapper.ProblemMapper;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import com.moonboardapp.user.model.User;
import com.moonboardapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final GradeRepository gradeRepository;

    private final UserRepository userRepository;

    @Transactional
    public ProblemDto createProblem(long userId, ProblemDto problemDto) {
        checkUser(userId);
        Grade grade = gradeRepository.findById(problemDto.getGrade()).orElseThrow();
        Problem problem = problemRepository.save(Problem.builder().name(problemDto.getName()).
                creatorId(userId).
                problemNumberField(problemDto.getProblemNumberField()).
                description(problemDto.getDescription()).
                videoUrl(problemDto.getVideoUrl()).
                grade(grade).
                publishedDate(Timestamp.valueOf(LocalDateTime.now())).build());

        return ProblemMapper.PROBLEM_MAPPER.toProblemDto(problem);
    }

    @Transactional
    public ProblemDto updateProblem(long userId, long problemId, ProblemUpdateDto changedTrack) {
        checkUser(userId);
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> new NotFoundException(String.format("Problem with id %d not found", problemId)));
        if (!changedTrack.getName().isEmpty()) {
            problem.setName(changedTrack.getName());
        }
        if (!changedTrack.getDescription().isEmpty()) {
            problem.setDescription(changedTrack.getDescription());
        }
        if (!changedTrack.getVideoUrl().isEmpty()) {
            problem.setVideoUrl(changedTrack.getVideoUrl());
        }

        return ProblemMapper.PROBLEM_MAPPER.toProblemDto(problem);
    }

    public ProblemDto getProblemById(long id) {
        return ProblemMapper.PROBLEM_MAPPER.toProblemDto(problemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Problem with id %d not found", id))));
    }

    public List<ProblemDto> getProblemsByUserId(long userId, Pageable pageable){
        checkUser(userId);
        List<Problem> problems = problemRepository.findByCreatorId(userId, pageable);
        List<ProblemDto> problemDtos = new ArrayList<>();

        for (Problem p : problems){
            problemDtos.add(ProblemMapper.PROBLEM_MAPPER.toProblemDto(p));
        }

        return problemDtos;
    }

    public List<ProblemDto> getAllProblems(Pageable pageable) {
        List<ProblemDto> problems = problemRepository.findAll(pageable).stream()
                .map(ProblemMapper.PROBLEM_MAPPER::toProblemDto)
                .collect(Collectors.toList());
        return problems;
    }

    public void deleteProblemById(long userId, long problemId){
        checkUser(userId);
        getProblemById(problemId);
        problemRepository.deleteById(problemId);
    }

    public void addRating(long problemId, int rating) {
        Problem problem = problemRepository.findById(problemId).orElseThrow( () ->
        new NotFoundException(String.format("Problem with id %d not found", problemId)));
        double totalScore  = problem.getRating() * problem.getClimbs() + rating;
        int updatedClimbs = problem.getClimbs() + 1;
        double updatedRating = totalScore / updatedClimbs;

        problem.setRating(updatedRating);
        problem.setClimbs(updatedClimbs);
    }

    public User checkUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id %d not found", userId)));
    }
}
