package com.code_hq.core.domain.score;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID>
{
}
