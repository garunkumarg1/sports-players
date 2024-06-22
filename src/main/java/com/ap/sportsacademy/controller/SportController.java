package com.ap.sportsacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.sportsacademy.model.Sport;
import com.ap.sportsacademy.service.SportService;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public ResponseEntity<List<Sport>> getSports(@RequestParam List<String> names) {
        List<Sport> sports = sportService.getSportsByName(names);
        return ResponseEntity.ok(sports);
    }
    
    @DeleteMapping("/{sportId}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long sportId) {
        sportService.deleteSport(sportId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Sport> createSport(@RequestBody Sport sport) {
        if (sport.getName() == null || sport.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Sport savedSport = sportService.save(sport);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSport);
    }

    @GetMapping("/{sportId}")
    public ResponseEntity<Sport> getSport(@PathVariable Long sportId) {
        Sport sport = sportService.getSport(sportId);
        return ResponseEntity.ok(sport);
    }

}

