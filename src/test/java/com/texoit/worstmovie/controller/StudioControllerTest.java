package com.texoit.worstmovie.controller;

import com.texoit.worstmovie.entity.StudioEntity;
import com.texoit.worstmovie.repository.StudioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StudioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudioRepository studioRepository;

    @BeforeEach
    void setup() {
        studioRepository.deleteAll();
        StudioEntity studio1 = new StudioEntity();
        studio1.setName("Studio 1");
        StudioEntity studio2 = new StudioEntity();
        studio2.setName("Studio 2");
        studioRepository.saveAll(List.of(studio1, studio2));
    }

    @DisplayName("Should find all studios")
    @Test
    void shouldReturnAllStudios() throws Exception {
        mockMvc.perform(get("/studios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Studio 1"))
                .andExpect(jsonPath("$[1].name").value("Studio 2"));
    }

    @DisplayName("Should get studio")
    @Test
    void shouldReturnStudio() throws Exception {
        StudioEntity studio = studioRepository.findAll().get(0);
        mockMvc.perform(get("/studios/{id}", studio.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(studio.getName()));
    }

    @DisplayName("Should create studio")
    @Test
    void shouldCreateStudio() throws Exception {
        StudioEntity newStudio = new StudioEntity();
        newStudio.setName("Studio 3");

        mockMvc.perform(post("/studios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Studio 3\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Studio 3"));
    }

    @DisplayName("Should update studio")
    @Test
    void shouldUpdateStudio() throws Exception {
        StudioEntity studio = studioRepository.findAll().get(0);

        mockMvc.perform(put("/studios/{id}", studio.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Studio\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Studio"));
    }

    @DisplayName("Should delete studio")
    @Test
    void shouldDeleteStudio() throws Exception {
        StudioEntity studio = studioRepository.findAll().get(0);

        mockMvc.perform(delete("/studios/{id}", studio.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/studios/{id}", studio.getId()))
                .andExpect(status().isNotFound());
    }
}