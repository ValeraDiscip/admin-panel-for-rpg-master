package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerFilter {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private long after;
    private long before;
    private boolean banned;
    private int minExperience;
    private int maxExperience;
    private int minLevel;
    private int maxLevel;
    private PlayerOrder order;
    private int pageNumber;
    private int pageSize;
}
