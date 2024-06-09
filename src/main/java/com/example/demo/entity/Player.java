package com.example.demo.entity;

import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class Player {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;
}
