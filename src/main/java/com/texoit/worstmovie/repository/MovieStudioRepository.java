package com.texoit.worstmovie.repository;

import com.texoit.worstmovie.entity.MovieStudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudioEntity,Long> {
}
