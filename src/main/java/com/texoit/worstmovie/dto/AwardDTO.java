package com.texoit.worstmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardDTO {

    private String producer;

    private int interval;

    private int previousWin;

    private int followingWin;

}
