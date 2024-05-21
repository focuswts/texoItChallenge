package com.texoit.worstmovie.controller;

import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public ResponseEntity<List<ProducerEntity>> getAllProducers() {
        List<ProducerEntity> producers = producerService.getProducers();
        return ResponseEntity.ok(producers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducerEntity> getProducerById(@PathVariable Long id) {
        ProducerEntity producer = producerService.getProducerById(id);
        return producer != null ? ResponseEntity.ok(producer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProducerEntity> createProducer(@RequestBody ProducerEntity producer) {
        ProducerEntity createdProducer = producerService.createProducer(producer);
        return ResponseEntity.status(201).body(createdProducer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProducerEntity> updateProducer(@PathVariable Long id, @RequestBody ProducerEntity producerDetails) {
        ProducerEntity updatedProducer = producerService.updateProducerById(id, producerDetails);
        return updatedProducer != null ? ResponseEntity.ok(updatedProducer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        producerService.deleteProducer(id);
        return ResponseEntity.noContent().build();
    }

}
