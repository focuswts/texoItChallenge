package com.texoit.worstmovie.dto;

import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.entity.StudioEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CsvColumnsResponseDTO {

    private final MovieEntity movieEntity;

    private final List<ProducerEntity> producers;

    private final List<StudioEntity> studios;

}
