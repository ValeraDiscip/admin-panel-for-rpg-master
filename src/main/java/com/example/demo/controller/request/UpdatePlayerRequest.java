package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.validation.YearRange;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UpdatePlayerRequest {
    @NotEmpty(message = "Name can not be empty.", )
    @Size(max = 12, message = "Name can not be more than 12 chars")
    private String name;
    @NotEmpty(message = "Title can not be empty.")
    @Size(max = 30, message = "Title can not be more than 30 chars")
    private String title;
    private Race race;
    private Profession profession;
    @YearRange(message = "Birthday must be from 2000 to 3000 (included)", startYear = 2000, endYear = 3000)
    private Date birthday;
    private boolean banned = false;
    @PositiveOrZero(message = "Experience must be positive or zero.")
    @Max(value = 10_000_000, message = "Experience can not be more than 10_000_000")
    private int experience;
}
