package com.example.demo.controller.response;

import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class PlayerResponse {
    private long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date birthday;
    private boolean banned;
    private int experience;
    private int level;
    private int untilNextLevel;
}
