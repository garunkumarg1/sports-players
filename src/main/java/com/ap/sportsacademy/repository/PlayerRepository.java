package com.ap.sportsacademy.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.sportsacademy.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    //SELECT * FROM players WHERE gender='male' AND level=2 AND age=45
    List<Player> findByGenderAndLevelAndAge(String gender, int level, int age);
    
    List<Player> findBySportsIsEmpty();
    
    Page<Player> findBySportsName(String sport, Pageable pageable);
    
}

