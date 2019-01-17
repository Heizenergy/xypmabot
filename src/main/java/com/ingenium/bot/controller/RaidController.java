package com.ingenium.bot.controller;

import com.ingenium.bot.entities.Raid;
import com.ingenium.bot.exceptions.PhraseNotFoundException;
import com.ingenium.bot.repository.RaidRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RaidController {

    public static RaidRepository raidRepo;

    RaidController(RaidRepository repository) {
        RaidController.raidRepo = repository;
    }

    @GetMapping("/raids")
    List<Raid> all() {
        List<Raid> temp = raidRepo.findAll();
        return temp;
    }

    @PostMapping("/raids")
    Raid newRaid(@RequestBody Raid newRaid) {
        return raidRepo.save(newRaid);
    }

    @GetMapping("/raids/{id}")
    Raid one(@PathVariable Long id) {
        return raidRepo.findById(id)
                .orElseThrow(() -> new PhraseNotFoundException(id));
    }

    @DeleteMapping("/raids/{id}")
    void deleteRaid(@PathVariable Long id) {
        raidRepo.deleteById(id);
    }
}