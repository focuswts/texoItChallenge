package com.texoit.worstmovie.repository;

import com.texoit.worstmovie.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<StudioEntity,Long> {
}
