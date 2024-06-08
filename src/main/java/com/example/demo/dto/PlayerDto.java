package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
@Builder
@Data
public class PlayerDto {
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
