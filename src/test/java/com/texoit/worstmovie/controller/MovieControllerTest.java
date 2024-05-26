package com.texoit.worstmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.texoit.worstmovie.dto.AwardIntervalDTO;
import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.entity.StudioEntity;
import com.texoit.worstmovie.repository.MovieRepository;
import com.texoit.worstmovie.repository.ProducerRepository;
import com.texoit.worstmovie.repository.StudioRepository;
import com.texoit.worstmovie.service.AwardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private AwardService awardService;

    @Autowired
    private ObjectMapper objectMapper;

    void setup() {
        movieRepository.deleteAll();
        producerRepository.deleteAll();
        studioRepository.deleteAll();

        ProducerEntity producer1 = new ProducerEntity();
        producer1.setName("Producer 1");
        ProducerEntity producer2 = new ProducerEntity();
        producer2.setName("Producer 2");
        producerRepository.saveAll(List.of(producer1, producer2));

        StudioEntity studio1 = new StudioEntity();
        studio1.setName("Studio 1");
        StudioEntity studio2 = new StudioEntity();
        studio2.setName("Studio 2");
        studioRepository.saveAll(List.of(studio1, studio2));

        MovieEntity movie1 = new MovieEntity();
        movie1.setTitle("Movie 1");
        movie1.setYear(2001);
        movie1.setWinner(true);
        movie1.setProducers(List.of(producer1));
        movie1.setStudios(List.of(studio1));

        MovieEntity movie2 = new MovieEntity();
        movie2.setTitle("Movie 2");
        movie2.setYear(2002);
        movie2.setWinner(false);
        movie2.setProducers(List.of(producer2));
        movie2.setStudios(List.of(studio2));

        movieRepository.saveAll(List.of(movie1, movie2));
    }

    @DisplayName("Should find all movies with producers and studios")
    @Test
    void shouldReturnAllMoviesWithProducersAndStudios() throws Exception {
        setup();

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].producers[0].name").value("Producer 1"))
                .andExpect(jsonPath("$[0].studios[0].name").value("Studio 1"))
                .andExpect(jsonPath("$[1].title").value("Movie 2"))
                .andExpect(jsonPath("$[1].producers[0].name").value("Producer 2"))
                .andExpect(jsonPath("$[1].studios[0].name").value("Studio 2"));
    }

    @DisplayName("Should get intervals")
    @DirtiesContext
    @Test
    void shouldGetIntervals() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movies/interval"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.min").isNotEmpty())
                .andExpect(jsonPath("$.max").isNotEmpty())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        AwardIntervalDTO awardIntervalDTO = objectMapper.readValue(jsonResponse, AwardIntervalDTO.class);

        assertNotNull(awardIntervalDTO);
        assertFalse(CollectionUtils.isEmpty(awardIntervalDTO.getMin()));
        assertFalse(CollectionUtils.isEmpty(awardIntervalDTO.getMax()));

        assertTrue(awardIntervalDTO.getMin().stream().allMatch(awardDTO -> awardDTO.getInterval() == 1));
        assertTrue(awardIntervalDTO.getMax().stream().allMatch(awardDTO -> awardDTO.getInterval() > 1));

    }

    @DisplayName("Should find all winner movies with producers and studios")
    @Test
    void shouldReturnAllWinnerMoviesWithProducersAndStudios() throws Exception {
        setup();

        mockMvc.perform(get("/movies/winner"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Movie 1"))
                .andExpect(jsonPath("$[0].producers[0].name").value("Producer 1"))
                .andExpect(jsonPath("$[0].studios[0].name").value("Studio 1"));
    }

    @DisplayName("Should get movie by ID with producers and studios")
    @Test
    void shouldReturnMovieByIdWithProducersAndStudios() throws Exception {
        setup();

        MovieEntity movie = movieRepository.findAll().get(0);
        mockMvc.perform(get("/movies/{id}", movie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(movie.getTitle()))
                .andExpect(jsonPath("$.producers[0].name").value("Producer 1"))
                .andExpect(jsonPath("$.studios[0].name").value("Studio 1"));
    }

    @DisplayName("Should create movie with producers and studios")
    @Test
    void shouldCreateMovieWithProducersAndStudios() throws Exception {
        setup();

        ProducerEntity producer = producerRepository.findAll().get(0);
        StudioEntity studio = studioRepository.findAll().get(0);

        MovieEntity newMovie = new MovieEntity();
        newMovie.setTitle("Movie 3");
        newMovie.setYear(2003);
        newMovie.setWinner(true);
        newMovie.setProducers(List.of(producer));
        newMovie.setStudios(List.of(studio));

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Movie 3"))
                .andExpect(jsonPath("$.producers[0].name").value(producer.getName()))
                .andExpect(jsonPath("$.studios[0].name").value(studio.getName()));
    }

    @DisplayName("Should update movie with producers and studios")
    @Test
    void shouldUpdateMovieWithProducersAndStudios() throws Exception {
        setup();

        MovieEntity movie = movieRepository.findAll().get(0);
        ProducerEntity newProducer = producerRepository.findAll().get(1);
        StudioEntity newStudio = studioRepository.findAll().get(1);

        MovieEntity updatedDetails = new MovieEntity();
        updatedDetails.setTitle("Updated Movie");
        updatedDetails.setYear(movie.getYear());
        updatedDetails.setWinner(movie.isWinner());
        updatedDetails.setProducers(List.of(newProducer));
        updatedDetails.setStudios(List.of(newStudio));

        mockMvc.perform(put("/movies/{id}", movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Movie"))
                .andExpect(jsonPath("$.producers[0]").isNotEmpty())
                .andExpect(jsonPath("$.studios[0].name").isNotEmpty());
    }

    @DisplayName("Should delete movie")
    @Test
    void shouldDeleteMovie() throws Exception {
        setup();

        MovieEntity movie = movieRepository.findAll().get(0);

        mockMvc.perform(delete("/movies/{id}", movie.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/movies/{id}", movie.getId()))
                .andExpect(status().isNotFound());
    }
}
