package com.ap.sportsacademy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ap.sportsacademy.model.Player;
import com.ap.sportsacademy.model.Sport;
import com.ap.sportsacademy.repository.PlayerRepository;
import com.ap.sportsacademy.repository.SportRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SportRepository sportRepository;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        sportRepository.deleteAll();
    }

    @Test
    void shouldGetSportsWithAssociatedPlayers() throws Exception {
        Sport sport1 = new Sport();
        sport1.setName("cricket");
        sportRepository.save(sport1);

        Sport sport2 = new Sport();
        sport2.setName("football");
        sportRepository.save(sport2);

        Player player = new Player();
        player.setEmail("player@xyz.com");
        player.setLevel(5);
        player.setAge(25);
        player.setGender("male");
        Set<Sport> sports = new HashSet<>();
        sports.add(sport1);
        player.setSports(sports);
        playerRepository.save(player);

        mockMvc.perform(get("/api/sports")
                .param("names", "cricket", "football"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("cricket"))
                .andExpect(jsonPath("$[0].players[0].email").value("player@xyz.com"));
    }

    @Test
    void shouldReturnNotFoundForNonExistentSport() throws Exception {
        mockMvc.perform(get("/api/sports/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteSportAndAssociatedData() throws Exception {
        Sport sport = new Sport();
        sport.setName("cricket");
        sportRepository.save(sport);

        mockMvc.perform(delete("/api/sports/" + sport.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/sports/" + sport.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldReturnBadRequestForInvalidSportName() throws Exception {
        Sport sport = new Sport();
        sport.setName(null);

        mockMvc.perform(post("/api/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null}"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void shouldCreateSportSuccessfully() throws Exception {
        String sportJson = "{\"name\":\"tennis\"}";

        mockMvc.perform(post("/api/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("tennis"));
    }
}
