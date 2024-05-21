package com.texoit.worstmovie.repository;

import com.texoit.worstmovie.entity.MovieProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducerEntity,Long> {
}
