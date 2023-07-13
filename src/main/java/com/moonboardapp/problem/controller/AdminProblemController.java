package com.moonboardapp.problem.controller;

import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/problem/admin")
@RequiredArgsConstructor
public class AdminProblemController {

    private final ProblemService problemService;

    @PatchMapping("{problemId}")
    public ProblemDto updateProblem(@PathVariable long problemId,
                                    @RequestBody ProblemUpdateDto problem) {
        return problemService.updateProblemByAdmin(problemId, problem);
    }

    @DeleteMapping("{problemId}")
    public void deleteProblemById(@PathVariable long problemId){
        problemService.deleteProblemByAdmin(problemId);
    }
}
