package com.vfa.controller;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Player;
import com.vfa.service.interfaces.PlayerService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(playerService.getById(id));
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<?> getPlayerInfo(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(playerService.getPlayerInfo(id));
    }

   /* @GetMapping("/team/{id}")
    public ResponseEntity<?> getByTeamId(@PathVariable(value = "id") int id,
                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(playerService.getByTeamId(id, pageable));
    }*/

    @GetMapping("/by-age")
    public ResponseEntity<?> getByPlayerAge(@RequestParam int from, int to) {
        return ResponseEntity.ok(playerService.getByAge(from, to));
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAll() {
        return ResponseEntity.ok(playerService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Player player) throws DuplicateDataException, BadRequestException {
        playerService.save(player);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAll(@RequestBody List<Player> player) {
        playerService.savePlayers(player);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Player player) {
        playerService.update(player);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        playerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
