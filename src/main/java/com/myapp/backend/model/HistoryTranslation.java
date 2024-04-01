package com.myapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "history_translation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "source")
    private String source;
    @Column(name = "target")
    private String target;

    @ManyToOne
    @JoinColumn(name = "translator_id")
    private Translator translator;
}
