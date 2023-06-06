package com.moonboardapp.problem.controller;

import com.moonboardapp.problem.dto.NewProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public Problem createProblem(@RequestHeader long userId,
                                 @RequestBody NewProblemDto problem) {
       return problemService.createProblem(userId, problem);
    }

    @PatchMapping("{problemId}")
    public Problem updateProblem(@RequestHeader long userId,
                               @PathVariable long problemId,
                               @RequestBody ProblemUpdateDto problem) {
       return problemService.updateProblem(userId, problemId, problem);
    }

    @GetMapping("{problemId}")
    public Problem getProblemById(@PathVariable long problemId) {
        return problemService.getProblemById(problemId);
    }

    @GetMapping("/user")
    public List<Problem> getProblemByUserId(@RequestHeader long userId){
        return problemService.getProblemsByUserId(userId);
    }

    @GetMapping
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @DeleteMapping("{problemId}")
    public void deleteTrackById(@RequestHeader long userId,
                                @PathVariable long problemId){
        problemService.deleteProblemById(userId, problemId);
    }

}
