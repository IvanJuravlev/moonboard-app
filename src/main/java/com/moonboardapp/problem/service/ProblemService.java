package com.moonboardapp.problem.service;

import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.grades.GradeRepository;
import com.moonboardapp.exception.NotFoundException;
import com.moonboardapp.problem.mapper.ProblemMapper;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public ProblemDto createProblem(long userId, ProblemDto problemDto) {
        /**Сделать маппер для сущности TrackCreate.*/

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
        /**Добавить проверку на пользователя обновляющего трассу
         * Добавить поле "Дата изменения. Может быть*/

        Problem problem = problemRepository.findById(problemId).orElseThrow(
                () -> new NotFoundException(String.format("Трасса с id %d не найдена", problemId)));
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

    public List<ProblemDto> getProblemsByUserId(long userId){
        /**Добавить проверку на пользователя*/

        List<Problem> problems = problemRepository.findByCreatorId(userId);
        List<ProblemDto> problemDtos = new ArrayList<>();

        for (Problem p : problems){
            problemDtos.add(ProblemMapper.PROBLEM_MAPPER.toProblemDto(p));
        }

        return problemDtos;
    }

    public List<ProblemDto> getAllProblems() {
        /**Добавить Pageble */
        List<Problem> problems = problemRepository.findAll();
        List<ProblemDto> problemDtos = new ArrayList<>();

        for (Problem p : problems){
            problemDtos.add(ProblemMapper.PROBLEM_MAPPER.toProblemDto(p));
        }
        return problemDtos;
    }

    public void deleteProblemById(long userId, long problemId){
        /**Добавить проверку на пользователя*/
        getProblemById(problemId);

        problemRepository.deleteById(problemId);
    }

}
