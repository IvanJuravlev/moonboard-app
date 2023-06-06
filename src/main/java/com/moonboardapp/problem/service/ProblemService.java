package com.moonboardapp.problem.service;

import com.moonboardapp.problem.dto.NewProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.grades.GradeRepository;
import com.moonboardapp.problem.exception.NotFoundException;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public Problem createProblem(long userId, NewProblemDto newProblemDto) {
        /**Сделать маппер для сущности TrackCreate.*/

        Grade grade = gradeRepository.findById(newProblemDto.getGrade()).orElseThrow();
        Problem track = Problem.builder().name(newProblemDto.getName()).
                creatorId(userId).
                problemNumberField(newProblemDto.getProblemNumberField()).
                description(newProblemDto.getDescription()).
                videoUrl(newProblemDto.getVideoUrl()).
                grade(grade).
                publishedDate(Timestamp.valueOf(LocalDateTime.now())).build();

        return problemRepository.save(track);
    }

    @Transactional
    public Problem updateProblem(long userId, long problemId, ProblemUpdateDto changedTrack) {
        /**Добавить проверку на пользователя обновляющего трассу
         * Добавить поле "Дата изменения. Может быть*/

        Problem track = getProblemById(problemId);
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

    public Problem getProblemById(long id) {
        return problemRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Трасса с id %d не найдена", id)));
    }

    public List<Problem> getProblemsByUserId(long userId){
        /**Добавить проверку на пользователя*/

        return problemRepository.findByCreatorId(userId);
    }

    public List<Problem> getAllProblems() {
        /**Добавить Pageble */
        return problemRepository.findAll();
    }

    public void deleteProblemById(long userId, long problemId){
        /**Добавить проверку на пользователя*/
        getProblemById(problemId);

        problemRepository.deleteById(problemId);
    }

}
