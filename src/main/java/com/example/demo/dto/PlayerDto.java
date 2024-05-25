package com.example.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlayerDto {
    private long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date birthday;
    private boolean banned = false;
    private int experience;
    private int level;
    private int untilNextLevel;
}
//здесь должны быть все данные или они будут только в обьекте player
