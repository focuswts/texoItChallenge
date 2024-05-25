package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.dto.AwardDTO;
import com.texoit.worstmovie.dto.AwardIntervalDTO;
import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.service.AwardService;
import com.texoit.worstmovie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AwardServiceImpl implements AwardService {

    private final MovieService movieService;

    @Override
    public AwardIntervalDTO getAwardsInterval() {
        List<MovieEntity> movies = movieService.getWinnerMovies();

        Map<String, List<Integer>> winnerYearsByProducer = new HashMap<>();

        movies.forEach(movie -> {
            int year = movie.getYear();
            List<ProducerEntity> producers = movie.getProducers();

            producers.forEach(
                    producer -> {
                        List<Integer> winnerYears = winnerYearsByProducer.getOrDefault(
                                producer.getName(),
                                new ArrayList<>()
                        );

                        winnerYears.add(year);

                        winnerYearsByProducer.put(producer.getName(), winnerYears);
                    }
            );

        });

        List<AwardDTO> awards = new ArrayList<>();
        winnerYearsByProducer.forEach((producer, winnerYears) -> {
            if (winnerYears.size() > 1) {
                Collections.sort(winnerYears);
                for (int i = 0; i < winnerYears.size() - 1; i++) {
                    int previousWin = winnerYears.get(i);
                    int followingWin = winnerYears.get(i + 1);
                    int interval = followingWin - previousWin;

                    if (interval == 1) {
                        awards.add(new AwardDTO(producer, interval, previousWin, followingWin));
                    }

                }
            }
        });

        AwardDTO maxIntervalAward = awards.stream()
                .max(Comparator.comparingInt(AwardDTO::getInterval))
                .orElse(null);


        return null;
    }
}
