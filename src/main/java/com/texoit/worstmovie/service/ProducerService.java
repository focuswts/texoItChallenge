package com.texoit.worstmovie.service;

import com.texoit.worstmovie.entity.ProducerEntity;

import java.util.List;

public interface ProducerService {

    List<ProducerEntity> getProducers();

    ProducerEntity getProducerById(Long id);

    ProducerEntity createProducer(ProducerEntity producer);

    ProducerEntity updateProducerById(Long producerId, ProducerEntity movie);

    void deleteProducer(Long id);

}
