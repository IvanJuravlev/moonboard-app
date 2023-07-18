package com.moonboardapp.trackingProblems.controller;

import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.UpdateTrackingProblemDto;
import com.moonboardapp.trackingProblems.service.TrackingProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/trackingProblem/admin")
@RequiredArgsConstructor
public class AdminTrackingProblemController {
    private final TrackingProblemService service;

    @PatchMapping("{trackingProblemId}")
    public TrackingProblemDto update(@PathVariable long trackingProblemId,
                                     @Valid @RequestBody UpdateTrackingProblemDto updateTrackingProblemDto) {
        return service.updateByAdmin(trackingProblemId, updateTrackingProblemDto);
    }

    @DeleteMapping("{trackingProblemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long trackingProblemId,
                           @RequestParam long userId) {
        service.deleteByAdmin(userId, trackingProblemId);
    }
}
