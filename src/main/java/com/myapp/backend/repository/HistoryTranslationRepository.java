package com.myapp.backend.repository;

import com.myapp.backend.model.HistoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryTranslationRepository extends JpaRepository<HistoryTranslation, Long> {
}
