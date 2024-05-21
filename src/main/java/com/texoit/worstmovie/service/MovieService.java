package com.texoit.worstmovie.service;

import com.texoit.worstmovie.entity.MovieEntity;

import java.util.List;

public interface MovieService {


    List<MovieEntity> getMovies();

    MovieEntity getMovieById(Long id);

    MovieEntity createMovie(MovieEntity movie);

    MovieEntity updateMovieById(Long movieId, MovieEntity movie);

    void deleteMovie(Long id);
}
