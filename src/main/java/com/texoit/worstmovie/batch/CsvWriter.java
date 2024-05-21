package com.texoit.worstmovie.batch;

import com.texoit.worstmovie.dto.CsvColumnsResponseDTO;
import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.MovieProducerEntity;
import com.texoit.worstmovie.entity.MovieStudioEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.entity.StudioEntity;
import com.texoit.worstmovie.repository.MovieProducerRepository;
import com.texoit.worstmovie.repository.MovieStudioRepository;
import com.texoit.worstmovie.service.MovieService;
import com.texoit.worstmovie.service.ProducerService;
import com.texoit.worstmovie.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvWriter implements ItemWriter<CsvColumnsResponseDTO> {

    private final MovieService movieService;
    private final StudioService studioService;
    private final ProducerService producerService;
    private final MovieStudioRepository movieStudioRepository;
    private final MovieProducerRepository movieProducerRepository;

    @Override
    public void write(Chunk<? extends CsvColumnsResponseDTO> chunk) throws Exception {
        try {
            List<CsvColumnsResponseDTO> items = (List<CsvColumnsResponseDTO>) chunk.getItems();

            items.forEach(this::handleSaving);
        } catch (Exception e) {
            throw e;
        }


    }

    private void handleSaving(CsvColumnsResponseDTO response) {
        MovieEntity movie = response.getMovieEntity();
        List<ProducerEntity> producers = response.getProducers();
        List<StudioEntity> studios = response.getStudios();

        MovieEntity createdMovie = movieService.createMovie(movie);

        List<Long> producersIds = getProducersIds(producers);
        saveMovieProducers(producersIds, createdMovie);

        List<Long> studiosIds = getStudiosIds(studios);
        saveMovieStudios(studiosIds, createdMovie);
    }

    private void saveMovieStudios(List<Long> studiosIds, MovieEntity createdMovie) {
        studiosIds.forEach(studioId -> movieStudioRepository.save(
                MovieStudioEntity.builder()
                        .movieId(createdMovie.getId())
                        .studioId(studioId)
                        .build()
        ));
    }

    private List<Long> getStudiosIds(List<StudioEntity> studios) {
        return studios.stream().map(studio -> {
            StudioEntity createdStudio = studioService.createStudio(studio);
            return createdStudio.getId();
        }).toList();
    }

    private void saveMovieProducers(List<Long> producersIds, MovieEntity createdMovie) {
        producersIds.forEach(producerId -> movieProducerRepository.save(
                MovieProducerEntity.builder()
                        .movieId(createdMovie.getId())
                        .producerId(producerId)
                        .build()
        ));
    }

    private List<Long> getProducersIds(List<ProducerEntity> producers) {
        return producers.stream().map(producerEntity -> {
            ProducerEntity producer = producerService.createProducer(producerEntity);
            return producer.getId();
        }).toList();
    }

}
