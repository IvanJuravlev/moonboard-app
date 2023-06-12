package com.moonboardapp.problem.repository;

import com.moonboardapp.problem.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByCreatorId(long creatorId);
}
