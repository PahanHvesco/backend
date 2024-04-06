package com.myapp.backend.repository;



import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.model.HistoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryTranslationRepository extends JpaRepository<HistoryTranslation, Long> {
    @Query("SELECT new com.myapp.backend.dto.TranslatorDto(t.id, t.en, t.ru) "
            + "FROM Translator t "
            + "JOIN t.historyTranslations ht "
            + "WHERE ht.source = :source AND ht.target = :target")
    List<TranslatorDto> getTranslationsBySourceAndTarget(@Param("source") String source, @Param("target") String target);
}
