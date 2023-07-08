package com.moonboardapp.problem.repository;

import com.moonboardapp.problem.model.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.From;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByCreatorId(long creatorId, Pageable pageable);
    Page<Problem> findAll(Pageable pageable);
}
