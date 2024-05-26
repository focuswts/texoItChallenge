package com.texoit.worstmovie.service.impl;

import com.texoit.worstmovie.dto.AwardIntervalDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class AwardServiceImplTest {

    @Autowired
    private AwardServiceImpl awardService;

    @DisplayName("Should get awards")
    @Test
    void shouldGetAwards(){
        AwardIntervalDTO awards = awardService.getAwardsInterval();

        assertNotNull(awards);
        assertFalse(CollectionUtils.isEmpty(awards.getMin()));
        assertFalse(CollectionUtils.isEmpty(awards.getMax()));
        assertTrue(awards.getMin().stream().allMatch(award -> award.getInterval() == 1));
        assertTrue(awards.getMax().stream().allMatch(award -> award.getInterval() > 1));

    }


}