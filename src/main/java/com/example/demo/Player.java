package com.example.demo;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
public class Player {

    @NotNull(groups = Get.class)
    private long id;
    @NotBlank
    @Size(max = 12, message = "Name can not be empty or more than 12 chars")
    private String name;
    @NotBlank
    @Size(max = 30, message = "Title can not be empty or more than 30 chars")
    private String title;
    @NotNull
    private Race race;
    @NotNull
    private Profession profession;
    @NotNull
    //как ограничить дату если принимается тип int?
    private Date birthday;
    private boolean banned = false;
    @PositiveOrZero
    @Max(10_000_000)
    private int experience;
    private int level;
    private int untilNextLevel;

    public interface Get {

    }

    public interface Create {

    }
}
