package com.example.demo.dto;

import com.example.demo.filter.PlayerOrder;
import lombok.Data;

import java.util.Date;

@Data
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
    private PlayerOrder order = PlayerOrder.ID;
    private Integer pageNumber = 0;
    private Integer pageSize = 3;
}