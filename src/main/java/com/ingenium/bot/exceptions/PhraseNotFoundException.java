package com.ingenium.bot.exceptions;

public class PhraseNotFoundException extends RuntimeException {

    public PhraseNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
