package com.texoit.worstmovie.repository;

import com.texoit.worstmovie.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity,Long> {
}
