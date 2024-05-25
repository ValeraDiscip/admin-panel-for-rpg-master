package com.example.demo.entity;

import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import lombok.Data;

import java.util.Date;
@Data
public class Player {
        private final long id;
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

