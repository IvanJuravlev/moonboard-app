package com.moonboardapp.trackingProblems.controller;

import com.moonboardapp.trackingProblems.dto.ShortTrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.UpdateTrackingProblemDto;
import com.moonboardapp.trackingProblems.service.TrackingProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequestMapping("/trackingProblem")
@RequiredArgsConstructor
public class TrackingProblemController {
    private final TrackingProblemService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackingProblemDto create(@Valid @RequestBody ShortTrackingProblemDto shortTrackingProblemDto) {
        return service.create(shortTrackingProblemDto);
    }

    @PatchMapping("{trackingProblemId}")
    public TrackingProblemDto update(@PathVariable long trackingProblemId,
                                     @Valid @RequestBody UpdateTrackingProblemDto updateTrackingProblemDto) {
        return service.update(trackingProblemId, updateTrackingProblemDto);
    }

    @GetMapping("{trackingProblemId}")
    public TrackingProblemDto getById(@PathVariable long trackingProblemId) {
        return service.getById(trackingProblemId);
    }

    @GetMapping
    public List<TrackingProblemDto> getByUserId(@RequestParam long userId,
                                                @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                @Positive @RequestParam (defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return service.getByUserId(userId, pageable);
    }

    @DeleteMapping("{trackingProblemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long trackingProblemId,
                           @RequestParam long userId) {
        service.deleteById(userId, trackingProblemId);
    }

}
