package com.ingenium.bot.controller;
import com.ingenium.bot.entities.Phrase;
import com.ingenium.bot.exceptions.PhraseNotFoundException;
import com.ingenium.bot.repository.PhraseRepo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhraseController {

    public static PhraseRepo repository;

    PhraseController(PhraseRepo repository) {
        PhraseController.repository = repository;
    }

    @GetMapping("/phrase")
    List<Phrase> all() {
        return repository.findAll();
    }

    @PostMapping("/phrase")
    Phrase newEmployee(@RequestBody Phrase newPhrase) {
        return repository.save(newPhrase);
    }

    @GetMapping("/phrase/{id}")
    Phrase one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PhraseNotFoundException(id));
    }

    @DeleteMapping("/phrase/{id}")
    void deletePhrase(@PathVariable Long id) {
        repository.deleteById(id);
    }
}