package com.example.demo.controller;

import com.example.demo.PlayerDto;
import com.example.demo.controller.request.PlayerRequest;
import com.example.demo.controller.request.UpdatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RequestMapping("/rest/players")
public interface MyController {

    @GetMapping
    ResponseEntity<List<PlayerDto>> getPlayerList(@RequestParam(value = "name", required = false)@Size(max = 12, message = "Name can not be more than 12 chars") String name,
                                                  @RequestParam(value = "title", required = false) @Size(max = 30, message = "Title can not be more than 30 chars") String title,
                                                  @RequestParam(value = "race", required = false) Race race, @RequestParam(value = "profession", required = false) Profession profession,
                                                  @RequestParam(value = "after", required = false,defaultValue = "0") long after,
                                                  @RequestParam(value = "before", required = false,defaultValue = "0") long before,
                                                  @RequestParam(value = "banned", required = false) boolean banned,
                                                  @RequestParam(value = "minExperience", required = false,defaultValue = "0") int minExperience,
                                                  @RequestParam(value = "maxExperience", required = false,defaultValue = "0") int maxExperience,
                                                  @RequestParam(value = "minLevel",required = false,defaultValue = "0") int minLevel,
                                                  @RequestParam(value = "maxValue",defaultValue = "0") int maxLevel,
                                                  @RequestParam(value = "order", required = false) PlayerOrder order,
                                                  @RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
                                                  @RequestParam(value = "pageSize", required = false,defaultValue = "3") int pageSize);
/*надо ведь ограничивать вводимые данные? но как тогда ограничить макс уровень
числа можно не ограчиничать в принципе просто проверка в базе данных в любом случае даст правильный результат
можно сразу здесь ограничить типа макс после 2099 и до 2001 типа такого и то же самое с опытом
*/
    @PostMapping
    ResponseEntity<PlayerDto> createNewPlayer(@Valid @RequestBody PlayerRequest createPlayerRequest);

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PositiveOrZero  @PathVariable int id);

    @PostMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayerById(@PositiveOrZero @PathVariable int id, @RequestBody UpdatePlayerRequest updatePlayerRequest);

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePlayerById(@PositiveOrZero @PathVariable int id);
}