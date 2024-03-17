package com.myapp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "history_language")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class HistoryLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "source_language")
    private String sourceLanguage;
    @Column(name = "target_language")
    private String targetLanguage;

    @ManyToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
    @OneToMany(mappedBy = "historyLanguage", cascade = CascadeType.ALL)
    private Set<History> histories;
}
