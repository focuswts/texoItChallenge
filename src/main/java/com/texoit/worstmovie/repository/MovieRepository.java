package com.texoit.worstmovie.repository;

import com.texoit.worstmovie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    List<MovieEntity> findByWinnerTrueOrderByYearDesc();

}
