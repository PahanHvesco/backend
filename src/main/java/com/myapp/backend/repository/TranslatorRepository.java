package com.myapp.backend.repository;

import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslatorRepository extends JpaRepository<Translator, Long> {
    @Query("SELECT historyTranslation FROM HistoryTranslation historyTranslation WHERE historyTranslation.translator.id = :id")
    List<HistoryTranslation> getAllHistoryTranslationById(@Param("id") long id);

    @Query("SELECT historyLanguage FROM HistoryLanguage historyLanguage WHERE historyLanguage.translator.id = :id")
    List<HistoryLanguage> getAllHistoryLanguageById(@Param("id") long id);
}
