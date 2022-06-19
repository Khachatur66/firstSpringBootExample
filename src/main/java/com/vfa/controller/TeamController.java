package com.vfa.controller;

import com.vfa.dto.response.TeamResponseDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;
import com.vfa.service.interfaces.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(teamService.getById(id));
    }

    @GetMapping("/player/{teamOrigin}")
    public ResponseEntity<?> getPaginationByTeamId(@PathVariable(value = "teamOrigin") String teamOrigin,
                                                   @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(teamService.getByTeamId(teamOrigin, pageable));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Integer> getByPlayers(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(teamService.countPlayers(id));
    }

/*    @GetMapping("/{id}")
    public ResponseEntity<List<Team>> getByPlayers(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(teamService.getPlayersNameById(id));
    }*/


    @GetMapping
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamService.getAll());
    }

    @GetMapping("/count")
    public ResponseEntity<List<TeamResponseDTO>> getCount() {
        return ResponseEntity.ok(teamService.getCountById());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Team team) throws DuplicateDataException, BadRequestException {
        teamService.save(team);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Team team) {
        teamService.update(team);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") int id) {
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
