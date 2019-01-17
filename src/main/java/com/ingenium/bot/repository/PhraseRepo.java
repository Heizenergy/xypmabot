package com.ingenium.bot.repository;

import com.ingenium.bot.entities.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhraseRepo extends JpaRepository<Phrase, Long> {

}