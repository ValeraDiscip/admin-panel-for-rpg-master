package com.example.demo.dto;

import com.example.demo.filter.PlayerOrder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PlayerFilter {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date after;
    private Date before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;
    private PlayerOrder order;
    private Integer pageNumber;
    private Integer pageSize;
}