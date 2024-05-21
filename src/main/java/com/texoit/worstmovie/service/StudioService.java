package com.texoit.worstmovie.service;

import com.texoit.worstmovie.entity.StudioEntity;

import java.util.List;

public interface StudioService {

    List<StudioEntity> getStudios();

    StudioEntity getStudioById(Long id);

    StudioEntity createStudio(StudioEntity studio);

    StudioEntity updateStudioById(Long studioId, StudioEntity studioDetails);

    void deleteStudio(Long id);

}
