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
    private boolean banned;
    private int minExperience;
    private int maxExperience;
    private int minLevel;
    private int maxLevel;
    private PlayerOrder order;
    private int pageNumber;
    private int pageSize;
    //TODO primitive
}
