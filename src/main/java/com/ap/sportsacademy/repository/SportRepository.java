package com.ap.sportsacademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ap.sportsacademy.model.Sport;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
    @Query("SELECT s FROM Sport s WHERE size(s.players) >= 2")
    List<Sport> findSportsWithMultiplePlayers();
    
    List<Sport> findByNameIn(List<String> names);
    
    @Query("SELECT s FROM Sport s WHERE size(s.players) = 0")
    List<Sport> findSportsWithNoPlayers();
}
