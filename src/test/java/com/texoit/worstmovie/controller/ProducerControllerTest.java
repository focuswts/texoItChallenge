package com.texoit.worstmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        producerRepository.deleteAll();
        ProducerEntity producer1 = new ProducerEntity();
        producer1.setName("Producer 1");
        ProducerEntity producer2 = new ProducerEntity();
        producer2.setName("Producer 2");
        producerRepository.saveAll(List.of(producer1, producer2));
    }

    @DisplayName("Should find all producers")
    @Test
    void shouldReturnAllProducers() throws Exception {
        mockMvc.perform(get("/producers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Producer 1"))
                .andExpect(jsonPath("$[1].name").value("Producer 2"));
    }

    @DisplayName("Should get producer")
    @Test
    void shouldReturnProducer() throws Exception {
        ProducerEntity producer = producerRepository.findAll().get(0);
        mockMvc.perform(get("/producers/{id}", producer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(producer.getName()));
    }

    @DisplayName("Should create producer")
    @Test
    void shouldCreateProducer() throws Exception {
        ProducerEntity newProducer = new ProducerEntity();
        newProducer.setName("Producer 3");

        mockMvc.perform(post("/producers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProducer)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Producer 3"));
    }

    @DisplayName("Should update producer")
    @Test
    void shouldUpdateProducer() throws Exception {
        ProducerEntity producer = producerRepository.findAll().get(0);
        ProducerEntity updatedDetails = new ProducerEntity();
        updatedDetails.setName("Updated Producer");

        mockMvc.perform(put("/producers/{id}", producer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Producer"));
    }

    @DisplayName("Should delete producer")
    @Test
    void shouldDeleteProducer() throws Exception {
        ProducerEntity producer = producerRepository.findAll().get(0);

        mockMvc.perform(delete("/producers/{id}", producer.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/producers/{id}", producer.getId()))
                .andExpect(status().isNotFound());
    }
}