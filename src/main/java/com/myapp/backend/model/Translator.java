package com.myapp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "translated_words")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Translator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "en")
    private String en;
    @Column(name = "ru")
    private String ru;
    @OneToMany(mappedBy = "translator", cascade = CascadeType.ALL)
    private Set<HistoryTranslation> historyTranslations;
    @OneToMany(mappedBy = "translator", cascade = CascadeType.ALL)
    private Set<HistoryLanguage> historyLanguages;
}
