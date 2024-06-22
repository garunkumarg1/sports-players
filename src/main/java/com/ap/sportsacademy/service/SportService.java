package com.ap.sportsacademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ap.sportsacademy.exception.ResourceNotFoundException;
import com.ap.sportsacademy.model.Sport;
import com.ap.sportsacademy.repository.SportRepository;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getSportsByName(List<String> names) {
        List<Sport> sports = sportRepository.findByNameIn(names);
        return sports;
    }
    
    public void deleteSport(Long sportId) {
        Sport sport = sportRepository.findById(sportId).orElseThrow(() -> new ResourceNotFoundException("Sport not found"));
        sportRepository.delete(sport);
    }
    
    public Sport save(Sport sport) {
        Sport savedSport = sportRepository.save(sport);
        return savedSport;
    }
    
    public Sport getSport(Long sportId) {
        Sport sport = sportRepository.findById(sportId).orElseThrow(() -> new ResourceNotFoundException("Sport not found"));
        return sport;
    }
}
