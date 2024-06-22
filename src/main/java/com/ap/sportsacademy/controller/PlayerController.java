package com.ap.sportsacademy.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.sportsacademy.model.Player;
import com.ap.sportsacademy.service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/no-sports")
    public ResponseEntity<List<Player>> getPlayersWithNoSports() {
        List<Player> players = playerService.getPlayersWithNoSports();
        return ResponseEntity.ok(players);
    }
    
    @PutMapping("/{playerId}/sports")
    public ResponseEntity<Player> updatePlayerSports(@PathVariable Long playerId, @RequestBody Set<Long> sportIds) {
        Player player = playerService.updatePlayerSports(playerId, sportIds);
        return ResponseEntity.ok(player);
    }
    
    @GetMapping
    public ResponseEntity<Page<Player>> getPlayers(
        @RequestParam(required = false) String sport,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Page<Player> players = playerService.getPlayers(sport, page, size);
        return ResponseEntity.ok(players);
    }

    
}

