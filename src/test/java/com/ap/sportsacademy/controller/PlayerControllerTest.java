package com.ap.sportsacademy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
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
public class PlayerControllerTest {

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
    void shouldGetPlayersWithNoSports() throws Exception {
        Player player = new Player();
        player.setEmail("arun@xyz.com");
        player.setLevel(5);
        player.setAge(25);
        player.setGender("male");
        player.setSports(Collections.emptySet());
        playerRepository.save(player);

        mockMvc.perform(get("/api/players/no-sports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("arun@xyz.com"));
    }

    @Test
    void shouldUpdatePlayerSports() throws Exception {
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
        player.setSports(new HashSet<>());
        playerRepository.save(player);

        mockMvc.perform(put("/api/players/" + player.getId() + "/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"" + sport1.getId() + "\", \"" + sport2.getId() + "\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sports.size()").value(2));
    }


    @Test
    void shouldGetPaginatedPlayerListWithSportsFilter() throws Exception {
        Sport sport1 = new Sport();
        sport1.setName("cricket");
        sportRepository.save(sport1);

        Sport sport2 = new Sport();
        sport2.setName("football");
        sportRepository.save(sport2);

        for (int i = 1; i <= 15; i++) {
            Player player = new Player();
            player.setEmail("player" + i + "@example.com");
            player.setLevel(5);
            player.setAge(25);
            player.setGender("male");
            if (i <= 12) {
                Set<Sport> sports = new HashSet<>();
                sports.add(sport1);
                player.setSports(sports);
            }else {
                Set<Sport> sports = new HashSet<>();
                sports.add(sport2);
                player.setSports(sports);
            }
            playerRepository.save(player);
        }

        mockMvc.perform(get("/api/players")
                .param("sport", "cricket")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(10))
                .andExpect(jsonPath("$.totalElements").value(12)); // since only 8 players have the "cricket" sport
    }
}
