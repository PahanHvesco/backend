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
    @Column(name = "date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
    @OneToMany(mappedBy = "historyTranslation", cascade = CascadeType.ALL)
    private Set<History> histories;
}
