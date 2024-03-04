package com.myapp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "history_translation")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class HistoryTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "translation_id")
    private Translator translator;
    @Column(name = "date")
    private Timestamp date;
    @ManyToMany
    @JoinTable(
            name = "history",
            joinColumns = @JoinColumn(name = "history_translation_id"),
            inverseJoinColumns = @JoinColumn(name = "history_language_id")
    )
    private Set<HistoryLanguage> historyLanguages;
}
