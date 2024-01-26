package com.wanted.preonboarding.stage.repository;

import com.wanted.preonboarding.stage.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
}