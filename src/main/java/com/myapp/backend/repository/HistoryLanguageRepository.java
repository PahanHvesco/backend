package com.myapp.backend.repository;

import com.myapp.backend.model.HistoryLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLanguageRepository extends JpaRepository<HistoryLanguage, Long> {
}

