package com.moonboardapp.problem.mapper;
import com.moonboardapp.problem.dto.ProblemDto;
import com.moonboardapp.problem.model.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProblemMapper {
    ProblemMapper PROBLEM_MAPPER = Mappers.getMapper(ProblemMapper.class);
    @Mapping(target = "grade", source = "problem.grade.gradeId")
    ProblemDto toProblemDto(Problem problem);

}
