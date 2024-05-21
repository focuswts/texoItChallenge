package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.entity.StudioEntity;
import com.texoit.worstmovie.repository.StudioRepository;
import com.texoit.worstmovie.service.StudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;

    @Override
    public List<StudioEntity> getStudios() {
        log.info("StudioServiceImpl.getStudios - start");

        List<StudioEntity> studios = studioRepository.findAll();

        log.info("StudioServiceImpl.getStudios - end");
        return studios;
    }

    @Override
    public StudioEntity getStudioById(Long id) {
        log.info("StudioServiceImpl.getStudioById - start: id={}", id);

        Optional<StudioEntity> studio = studioRepository.findById(id);

        if (studio.isPresent()) {
            log.info("StudioServiceImpl.getStudioById - end: found");
            return studio.get();
        }

        log.warn("StudioServiceImpl.getStudioById - end: not found");
        return null;
    }

    @Override
    public StudioEntity createStudio(StudioEntity studio) {
        log.info("StudioServiceImpl.createStudio - start: {}", studio);

        StudioEntity savedStudio = studioRepository.save(studio);

        log.info("StudioServiceImpl.createStudio - end: {}", savedStudio);
        return savedStudio;
    }

    @Override
    public StudioEntity updateStudioById(Long studioId, StudioEntity studioDetails) {
        log.info("StudioServiceImpl.updateStudioById - start: studioId={}, studioDetails={}", studioId, studioDetails);
        Optional<StudioEntity> studioOptional = studioRepository.findById(studioId);
        if (studioOptional.isPresent()) {
            StudioEntity studio = studioOptional.get();
            studio.setName(studioDetails.getName());

            StudioEntity updatedStudio = studioRepository.save(studio);
            log.info("StudioServiceImpl.updateStudioById - end: {}", updatedStudio);
            return updatedStudio;
        }

        log.warn("StudioServiceImpl.updateStudioById - end: not found");
        return null;
    }

    @Override
    public void deleteStudio(Long id) {
        log.info("StudioServiceImpl.deleteStudio - start: id={}", id);
        if (studioRepository.existsById(id)) {

            studioRepository.deleteById(id);
            log.info("StudioServiceImpl.deleteStudio - end: deleted");
        } else {
            log.warn("StudioServiceImpl.deleteStudio - end: not found");
        }
    }
}
