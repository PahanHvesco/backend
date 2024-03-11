package com.myapp.backend.dto;

import com.myapp.backend.model.Translator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private long id;
    private String nameTag;
    private Set<Translator> translators;
}
