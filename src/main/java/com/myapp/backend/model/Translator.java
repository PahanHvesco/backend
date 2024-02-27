package com.myapp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "translated_words")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Translator {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "en")
    private String en;
    @Column(name = "ru")
    private String ru;
}
