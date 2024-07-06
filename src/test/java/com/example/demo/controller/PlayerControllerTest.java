package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.dto.Profession;
import com.example.demo.dto.Race;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    PlayerController playerController;

    @Test
    public void getPlayerListTest() throws Exception {

        this.mockMvc.perform(get("/rest/players").param("name", "Test")
                        .param("title", "Test")
                        .param("race", "HUMAN")
                        .param("profession", "PALADIN")
                        .param("banned", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value("firstTest"))
                .andExpect(jsonPath("$.[0].title").value("firstTest"))
                .andExpect(jsonPath("$.[0].race").value("HUMAN"))
                .andExpect(jsonPath("$.[0].profession").value("PALADIN"))
                .andExpect(jsonPath("$.[0].birthday").value("1593550800000"))
                .andExpect(jsonPath("$.[0].banned").value("true"))
                .andExpect(jsonPath("$.[0].experience").value("0"))
                .andExpect(jsonPath("$.[0].level").value("0"))
                .andExpect(jsonPath("$.[0].untilNextLevel").value("100"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].name").value("secondTest"))
                .andExpect(jsonPath("$.[1].title").value("secondTest"))
                .andExpect(jsonPath("$.[1].race").value("HUMAN"))
                .andExpect(jsonPath("$.[1].profession").value("PALADIN"))
                .andExpect(jsonPath("$.[1].birthday").value("1593550800000"))
                .andExpect(jsonPath("$.[1].banned").value("true"))
                .andExpect(jsonPath("$.[1].experience").value("0"))
                .andExpect(jsonPath("$.[1].level").value("0"))
                .andExpect(jsonPath("$.[1].untilNextLevel").value("100"))
                .andExpect(jsonPath("$.[2].id").value("5"))
                .andExpect(jsonPath("$.[2].name").value("thirdTest"))
                .andExpect(jsonPath("$.[2].title").value("thirdTest"))
                .andExpect(jsonPath("$.[2].race").value("HUMAN"))
                .andExpect(jsonPath("$.[2].profession").value("PALADIN"))
                .andExpect(jsonPath("$.[2].birthday").value("1593550800000"))
                .andExpect(jsonPath("$.[2].banned").value("true"))
                .andExpect(jsonPath("$.[2].experience").value("0"))
                .andExpect(jsonPath("$.[2].level").value("0"))
                .andExpect(jsonPath("$.[2].untilNextLevel").value("100"));
    }

    @Test
    public void getPlayerCountTest() throws Exception {
        this.mockMvc.perform(get("/rest/players/count")
                        .param("name", "Test")
                        .param("title", "Test")
                        .param("race", "HUMAN")
                        .param("profession", "PALADIN")
                        .param("banned", "true")
                        .param("maxExperience", "0")
                        .param("maxLevel", "0"))
                .andDo(print())
                .andExpect(jsonPath("$").value(3))
                .andExpect(status().isOk());
    }

    @Test
    public void createPlayerTest() throws Exception {

        CreatePlayerRequest createPlayerRequest = createPlayerRequest();

        String newPlayerString = mapper.writeValueAsString(createPlayerRequest);

        mockMvc.perform(post("/rest/players").contentType(APPLICATION_JSON).content(newPlayerString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(createPlayerRequest.getName()))
                .andExpect(jsonPath("$.title").value(createPlayerRequest.getTitle()))
                .andExpect(jsonPath("$.race").value(createPlayerRequest.getRace().name()))
                .andExpect(jsonPath("$.profession").value(createPlayerRequest.getProfession().name()))
                .andExpect(jsonPath("$.birthday").value(createPlayerRequest.getBirthday()))
                .andExpect(jsonPath("$.banned").value(createPlayerRequest.isBanned()))
                .andExpect(jsonPath("$.experience").value(createPlayerRequest.getExperience()))
                .andExpect(jsonPath("$.level").value("0"))
                .andExpect(jsonPath("$.untilNextLevel").value("100"));
        //обязательно удалять игрока после теста? в моем тесты я так глянул что ничего не случится в любом порядке выполнения тесты не сломаются
    }

    @Test
    public void createPlayerWithNotAllParamsTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();
        createPlayerRequest.setRace(null);
        createPlayerRequest.setProfession(null);

        String newPlayerString = mapper.writeValueAsString(createPlayerRequest);

        mockMvc.perform(post("/rest/players").contentType(APPLICATION_JSON).content(newPlayerString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPlayerWithEmptyNameTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();
        createPlayerRequest.setName("");

        String newPlayerString = mapper.writeValueAsString(createPlayerRequest);

        mockMvc.perform(post("/rest/players").contentType(APPLICATION_JSON).content(newPlayerString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPlayerWithTooLargeNameTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();
        createPlayerRequest.setName("ThisNameIsTooLongForCreating");

        String newPlayerString = mapper.writeValueAsString(createPlayerRequest);

        mockMvc.perform(post("/rest/players").contentType(APPLICATION_JSON).content(newPlayerString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPlayerWithTooLargeTitleTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();
        createPlayerRequest.setTitle("ThisTitleIsTooLongForCreatingggggg");

        String newPlayerString = mapper.writeValueAsString(createPlayerRequest);

        mockMvc.perform(post("/rest/players").contentType(APPLICATION_JSON).content(newPlayerString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPlayerByIdTest() throws Exception {
        this.mockMvc.perform(get("/rest/players/{id}", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("findById"))
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.race").value("DWARF"))
                .andExpect(jsonPath("$.profession").value("CLERIC"))
                .andExpect(jsonPath("$.birthday").value("1614373200000"))
                .andExpect(jsonPath("$.banned").value("false"))
                .andExpect(jsonPath("$.experience").value("4000"))
                .andExpect(jsonPath("$.level").value("8"))
                .andExpect(jsonPath("$.untilNextLevel").value("500"));
    }

    @Test
    public void getNotExistedPlayerByIdTest() throws Exception {
        this.mockMvc.perform(get("/rest/players/{id}", 555))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlayerByNotValidIdTest() throws Exception {
        this.mockMvc.perform(get("/rest/players/{id}", -1))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePlayerTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();

        PlayerResponse playerResponse = playerController.createNewPlayer(createPlayerRequest);
        long createdPlayerId = playerResponse.getId();

        UpdatePlayerRequest updatePlayerRequest = new UpdatePlayerRequest();
        updatePlayerRequest.setName("updatedName");
        updatePlayerRequest.setRace(Race.ORC);
        updatePlayerRequest.setProfession(Profession.CLERIC);
        updatePlayerRequest.setBanned(true);

        mockMvc.perform(post("/rest/players/{id}", createdPlayerId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatePlayerRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdPlayerId))
                .andExpect(jsonPath("$.name").value("updatedName"))
                .andExpect(jsonPath("$.title").value(createPlayerRequest.getTitle()))
                .andExpect(jsonPath("$.race").value("ORC"))
                .andExpect(jsonPath("$.profession").value("CLERIC"))
                .andExpect(jsonPath("$.birthday").value(createPlayerRequest.getBirthday()))
                .andExpect(jsonPath("$.banned").value(true))
                .andExpect(jsonPath("$.experience").value(createPlayerRequest.getExperience()));
    }

    @Test
    public void updateNotExistedPlayerTest() throws Exception {
        UpdatePlayerRequest updatePlayerRequest = new UpdatePlayerRequest();
        updatePlayerRequest.setName("updatedName");
        updatePlayerRequest.setRace(Race.ORC);
        updatePlayerRequest.setProfession(Profession.CLERIC);
        updatePlayerRequest.setBanned(true);

        mockMvc.perform(post("/rest/players/{id}", 7777)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatePlayerRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePlayerByNotValidIdTest() throws Exception {
        UpdatePlayerRequest updatePlayerRequest = new UpdatePlayerRequest();
        updatePlayerRequest.setName("updatedName");
        updatePlayerRequest.setRace(Race.ORC);
        updatePlayerRequest.setProfession(Profession.CLERIC);
        updatePlayerRequest.setBanned(true);

        mockMvc.perform(post("/rest/players/{id}", -1)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatePlayerRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletePlayerTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = createPlayerRequest();

        PlayerResponse playerResponse = playerController.createNewPlayer(createPlayerRequest);
        long createdPlayerId = playerResponse.getId();

        mockMvc.perform(delete("/rest/players/{id}", createdPlayerId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistedPlayerTest() throws Exception {

        mockMvc.perform(delete("/rest/players/{id}", 99999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePlayerByNotValidIdTest() throws Exception {

        mockMvc.perform(delete("/rest/players/{id}", -1))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private CreatePlayerRequest createPlayerRequest() {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName("createdName");
        createPlayerRequest.setTitle("createdTitle");
        createPlayerRequest.setRace(Race.ELF);
        createPlayerRequest.setProfession(Profession.PALADIN);
        createPlayerRequest.setBirthday(new Date(1614373200000L));
        createPlayerRequest.setBanned(false);
        createPlayerRequest.setExperience(0);
        return createPlayerRequest;
    }
}
