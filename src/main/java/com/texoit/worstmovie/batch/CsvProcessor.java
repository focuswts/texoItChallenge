package com.texoit.worstmovie.batch;

import com.texoit.worstmovie.dto.CsvColumnsDTO;
import com.texoit.worstmovie.dto.CsvColumnsResponseDTO;
import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.entity.StudioEntity;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.List;


public class CsvProcessor implements ItemProcessor<CsvColumnsDTO, CsvColumnsResponseDTO> {

    @Override
    public CsvColumnsResponseDTO process(CsvColumnsDTO item) throws Exception {

        MovieEntity movie = MovieEntity.builder()
                .title(item.getTitle())
                .winner(Boolean.parseBoolean(item.getWinner()))
                .build();

        String[] studios = item.getStudios().split(",");
        List<StudioEntity> studiosEntities = getStudios(studios);

        String[] producers = item.getProducers().split(",");
        List<ProducerEntity> producersEntities = getProducers(producers);

        return CsvColumnsResponseDTO.builder()
                .producers(producersEntities)
                .movieEntity(movie)
                .studios(studiosEntities)
                .build();
    }

    private static List<ProducerEntity> getProducers(String[] producers) {
        return Arrays.stream(producers)
                .map(producer -> ProducerEntity.builder()
                        .name(producer)
                        .build())
                .toList();
    }

    private static List<StudioEntity> getStudios(String[] studios) {
        return Arrays.stream(studios)
                .map(studio -> StudioEntity.builder()
                        .name(studio)
                        .build())
                .toList();
    }

}
