package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.repository.MovieRepository;
import com.texoit.worstmovie.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieEntity> getMovies() {
        log.info("MovieServiceImpl.getMovies - start");

        List<MovieEntity> movies = movieRepository.findAll();

        log.info("MovieServiceImpl.getMovies - end");
        return movies;
    }

    @Override
    public MovieEntity getMovieById(Long id) {
        log.info("MovieServiceImpl.getMovieById - start: id={}", id);

        Optional<MovieEntity> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            log.info("MovieServiceImpl.getMovieById - end: found");
            return movie.get();
        }

        log.warn("MovieServiceImpl.getMovieById - end: not found");
        return null;
    }

    @Override
    @Transactional
    public MovieEntity createMovie(MovieEntity movie) {
        log.info("MovieServiceImpl.createMovie - start: {}", movie);

        MovieEntity savedMovie = movieRepository.save(movie);

        log.info("MovieServiceImpl.createMovie - end: {}", savedMovie);
        return savedMovie;
    }

    @Override
    @Transactional
    public MovieEntity updateMovieById(Long movieId, MovieEntity movieDetails) {
        log.info("MovieServiceImpl.updateMovieById - start: movieId={}, movieDetails={}", movieId, movieDetails);
        Optional<MovieEntity> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            MovieEntity movie = movieOptional.get();

            movie.setTitle(movieDetails.getTitle());
            movie.setYear(movieDetails.getYear());
            movie.setWinner(movieDetails.isWinner());

            MovieEntity updatedMovie = movieRepository.save(movie);
            log.info("MovieServiceImpl.updateMovieById - end: {}", updatedMovie);
            return updatedMovie;
        }

        log.warn("MovieServiceImpl.updateMovieById - end: not found");
        return null;

    }

    @Override
    public void deleteMovie(Long id) {
        log.info("MovieServiceImpl.deleteMovie - start: id={}", id);
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            log.info("MovieServiceImpl.deleteMovie - end: deleted");
        }

        log.warn("MovieServiceImpl.deleteMovie - end: not found");
    }

}
