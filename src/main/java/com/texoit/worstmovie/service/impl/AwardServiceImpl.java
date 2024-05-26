package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.dto.AwardDTO;
import com.texoit.worstmovie.dto.AwardIntervalDTO;
import com.texoit.worstmovie.entity.MovieEntity;
import com.texoit.worstmovie.entity.ProducerEntity;
import com.texoit.worstmovie.service.AwardService;
import com.texoit.worstmovie.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwardServiceImpl implements AwardService {

    private final MovieService movieService;

    @Transactional
    @Override
    public AwardIntervalDTO getAwardsInterval() {
        log.info("AwardServiceImpl.getAwardsInterval - start");
        Map<String, List<Integer>> winnerYearsByProducer = getWinnerYearsByProducer();

        List<AwardDTO> minAwards = new ArrayList<>();
        List<AwardDTO> maxAwards = new ArrayList<>();

        winnerYearsByProducer.forEach((producer, winnerYears) -> {
            if (winnerYears.size() > 1) {
                Collections.sort(winnerYears);

                for (int i = 0; i < winnerYears.size() - 1; i++) {
                    int previousWin = winnerYears.get(i);
                    int followingWin = winnerYears.get(i + 1);
                    int interval = followingWin - previousWin;

                    AwardDTO award = AwardDTO.builder()
                            .producer(producer)
                            .interval(interval)
                            .previousWin(previousWin)
                            .followingWin(followingWin)
                            .build();

                    if (interval == 1) {
                        minAwards.add(
                                award
                        );
                    }

                    if (interval > 1) {
                        maxAwards.add(
                                award
                        );
                    }

                    log.debug("AwardServiceImpl.getAwardsInterval - award: [{}]", award);
                }
            }
        });

        minAwards.sort(
                Comparator.comparingInt(AwardDTO::getInterval)
        );

        maxAwards.sort(
                Comparator.comparingInt(AwardDTO::getInterval)
        );

        log.info("AwardServiceImpl.getAwardsInterval - end");
        return AwardIntervalDTO.builder()
                .min(minAwards)
                .max(maxAwards)
                .build();
    }

    private Map<String, List<Integer>> getWinnerYearsByProducer() {
        List<MovieEntity> movies = movieService.getWinnerMovies();

        Map<String, List<Integer>> winnerYearsByProducer = new HashMap<>();

        movies.forEach(movie -> {
            int year = movie.getYear();
            List<ProducerEntity> producers = movie.getProducers();

            producers.forEach(producer -> {
                List<Integer> winnerYears = winnerYearsByProducer.getOrDefault(
                        producer.getName(),
                        new ArrayList<>()
                );

                winnerYears.add(year);
                winnerYearsByProducer.put(producer.getName(), winnerYears);
            });

        });

        return winnerYearsByProducer;
    }
}
