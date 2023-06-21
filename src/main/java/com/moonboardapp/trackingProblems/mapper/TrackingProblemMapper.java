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

    @Mapping(target = "problem.problemId", source = "problem")
    @Mapping(target = "user.userId", source = "user")
    TrackingProblem toTrackingProblemFromShortDto(ShortTrackingProblemDto shortTrackingProblemDto);

    @Mapping(target = "problem", source = "problem.problemId")
    @Mapping(target = "user", source = "user.userId")
    ShortTrackingProblemDto toShortTrackingProblemDto(TrackingProblem trackingProblem);

    @Mapping(target = "problem", source = "problem.problemId")
    @Mapping(target = "user", source = "user.userId")
    TrackingProblemDto toTrackingProblemDto(TrackingProblem trackingProblem);


}
