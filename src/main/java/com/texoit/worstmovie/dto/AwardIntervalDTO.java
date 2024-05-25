package com.texoit.worstmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AwardIntervalDTO {

    private List<AwardDTO> min;

    private List<AwardDTO> max;

}
