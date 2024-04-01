package com.myapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "translator")
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(
            name = "translator_tag",
            joinColumns = {@JoinColumn(name = "translator_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags;
}
