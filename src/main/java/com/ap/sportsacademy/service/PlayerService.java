package com.ap.sportsacademy.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ap.sportsacademy.exception.ResourceNotFoundException;
import com.ap.sportsacademy.model.Player;
import com.ap.sportsacademy.model.Sport;
import com.ap.sportsacademy.repository.PlayerRepository;
import com.ap.sportsacademy.repository.SportRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private SportRepository sportRepository;

    public List<Player> getPlayersWithNoSports() {
        List<Player> players = playerRepository.findBySportsIsEmpty();
        return players;
    }
    
    public Player updatePlayerSports(Long playerId, Set<Long> sportIds) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Player not found"));
        Set<Sport> sports = new HashSet<>(sportRepository.findAllById(sportIds));
        player.setSports(sports);
        playerRepository.save(player);
        return player;
    }
    
    public Page<Player> getPlayers(String sport, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Player> players;
        if (sport != null) {
            players = playerRepository.findBySportsName(sport, pageable);
        } else {
            players = playerRepository.findAll(pageable);
        }
        return players;
    }
}

