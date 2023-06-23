package com.moonboardapp.trackingProblems.repository;

import com.moonboardapp.trackingProblems.model.TrackingProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingProblemRepository extends JpaRepository<TrackingProblem, Long> {
    List<TrackingProblem> findByUserUserId(long userId, Pageable pageable);
}
