package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.repository.ProducerRepository;
import com.texoit.worstmovie.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    @Override
    public List<ProducerEntity> getProducers() {
        log.info("ProducerServiceImpl.getProducers - start");

        List<ProducerEntity> producers = producerRepository.findAll();

        log.info("ProducerServiceImpl.getProducers - end");
        return producers;
    }

    @Override
    public ProducerEntity getProducerById(Long id) {
        log.info("ProducerServiceImpl.getProducerById - start: id={}", id);

        Optional<ProducerEntity> producer = producerRepository.findById(id);

        if (producer.isPresent()) {
            log.info("ProducerServiceImpl.getProducerById - end: found");
            return producer.get();
        }

        log.warn("ProducerServiceImpl.getProducerById - end: not found");
        return null;
    }

    @Override
    public ProducerEntity createProducer(ProducerEntity producer) {
        log.info("ProducerServiceImpl.createProducer - start: {}", producer);

        ProducerEntity savedProducer = producerRepository.save(producer);

        log.info("ProducerServiceImpl.createProducer - end: {}", savedProducer);
        return savedProducer;
    }

    @Override
    public ProducerEntity updateProducerById(Long producerId, ProducerEntity producerDetails) {
        log.info("ProducerServiceImpl.updateProducerById - start: producerId={}, producerDetails={}", producerId, producerDetails);
        Optional<ProducerEntity> producerOptional = producerRepository.findById(producerId);
        if (producerOptional.isPresent()) {
            ProducerEntity producer = producerOptional.get();
            producer.setName(producerDetails.getName());

            ProducerEntity updatedProducer = producerRepository.save(producer);
            log.info("ProducerServiceImpl.updateProducerById - end: {}", updatedProducer);
            return updatedProducer;
        }

        log.warn("ProducerServiceImpl.updateProducerById - end: not found");
        return null;
    }

    @Override
    public void deleteProducer(Long id) {
        log.info("ProducerServiceImpl.deleteProducer - start: id={}", id);
        if (producerRepository.existsById(id)) {

            producerRepository.deleteById(id);
            log.info("ProducerServiceImpl.deleteProducer - end: deleted");
        } else {
            log.warn("ProducerServiceImpl.deleteProducer - end: not found");
        }
    }

}
