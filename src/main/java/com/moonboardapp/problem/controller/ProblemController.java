package com.moonboardapp.problem.controller;

import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.dto.ProblemUpdateDto;
import com.moonboardapp.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public ProblemDto createProblem(@RequestHeader long userId,
                                 @RequestBody ProblemDto problem) {
       return problemService.createProblem(userId, problem);
    }

    @PatchMapping("{problemId}")
    public ProblemDto updateProblem(@RequestHeader long userId,
                               @PathVariable long problemId,
                               @RequestBody ProblemUpdateDto problem) {
       return problemService.updateProblem(userId, problemId, problem);
    }

    @GetMapping("{problemId}")
    public ProblemDto getProblemById(@PathVariable long problemId) {
        return problemService.getProblemById(problemId);
    }

    @GetMapping("/user")
    public List<ProblemDto> getProblemByUserId(@RequestHeader long userId,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                               @Positive @RequestParam (defaultValue = "20") int size){
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return problemService.getProblemsByUserId(userId, pageRequest);
    }

    @GetMapping
    public List<ProblemDto> getAllProblems(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                           @Positive @RequestParam (defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return problemService.getAllProblems(pageRequest);
    }

    @GetMapping("/climbs")
    public List<ProblemDto> getAllProblemsByClimbs(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                   @Positive @RequestParam (defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return problemService.getAllProblemsByClimbs(pageRequest);
    }

    @DeleteMapping("{problemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProblemById(@RequestHeader long userId,
                                @PathVariable long problemId){
        problemService.deleteProblemById(userId, problemId);
    }

}
