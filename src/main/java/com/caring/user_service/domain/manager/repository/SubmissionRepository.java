package com.caring.user_service.domain.manager.repository;

import com.caring.user_service.domain.manager.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
