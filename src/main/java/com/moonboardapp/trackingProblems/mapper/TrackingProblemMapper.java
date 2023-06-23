package com.moonboardapp.trackingProblems.mapper;

import com.moonboardapp.trackingProblems.dto.ShortTrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.model.TrackingProblem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrackingProblemMapper {
    TrackingProblemMapper TRACKING_PROBLEM_MAPPER = Mappers.getMapper(TrackingProblemMapper.class);

    @Mapping(target = "problem.problemId", source = "problemId")
    @Mapping(target = "user.userId", source = "userId")
    TrackingProblem toTrackingProblemFromShortDto(ShortTrackingProblemDto shortTrackingProblemDto);

    @Mapping(target = "problemId", source = "problem.problemId")
    @Mapping(target = "userId", source = "user.userId")
    ShortTrackingProblemDto toShortTrackingProblemDto(TrackingProblem trackingProblem);

    @Mapping(target = "problemId", source = "problem.problemId")
    @Mapping(target = "userId", source = "user.userId")
    TrackingProblemDto toTrackingProblemDto(TrackingProblem trackingProblem);


}
