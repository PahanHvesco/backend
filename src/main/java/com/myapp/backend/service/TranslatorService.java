package com.myapp.backend.service;

import com.myapp.backend.dto.TranslatorDto;
import com.myapp.backend.mapper.TranslatorMapper;
import com.myapp.backend.model.HistoryLanguage;
import com.myapp.backend.model.HistoryTranslation;
import com.myapp.backend.model.Tag;
import com.myapp.backend.model.Translator;
import com.myapp.backend.repository.TranslatorRepository;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TranslatorService {
    private final TranslatorRepository translatorRepository;
    public TranslatorService(TranslatorRepository translatorRepository){
        this.translatorRepository = translatorRepository;
    }

    @Transactional
    public Translator createTranslator(Translator translator) {
        return translatorRepository.save(translator);
    }

    public List<Translator> getAllTranslators() {
        return translatorRepository.findAll();
    }

    public Translator getTranslatorById(long id) {
        return translatorRepository.findById(id).orElse(null);
    }

    public Translator updateTranslator(long id, Translator updatedTranslator) {
        Optional<Translator> existingTranslator = translatorRepository.findById(id);
        if (existingTranslator.isPresent()) {
            Translator translatorToUpdate = existingTranslator.get();
            translatorToUpdate.setEn(updatedTranslator.getEn());
            translatorToUpdate.setRu(updatedTranslator.getRu());
            return translatorRepository.save(translatorToUpdate);
        }
        return null;
    }

    public void deleteTranslatorById(long id) {
        translatorRepository.deleteById(id);
    }

//    public void addTagToTranslator(long id, String nameTag) {
//        Tag tag = tagService.addTranslatorToTag(nameTag, getTranslatorById(id));
//        Translator translator = getTranslatorById(id);
//        translator.getTags().add(tag);
//        updateTranslator(id, translator);
//
//    }
}
