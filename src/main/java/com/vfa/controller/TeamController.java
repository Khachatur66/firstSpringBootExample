package com.vfa.controller;

import com.vfa.dto.response.TeamResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;
import com.vfa.service.interfaces.TeamService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/teamId/{id}")
    public ResponseEntity<Object> getTeamById(@PathVariable(value = "id") int id) throws BadRequestException {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping("/player/{teamOrigin}")
    public ResponseEntity<?> getPaginationByTeamId(@PathVariable(value = "teamOrigin") String teamOrigin,
                                                   @PageableDefault Pageable pageable) throws InterruptedException {
        return ResponseEntity.ok(teamService.getByTeamId(teamOrigin, pageable));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<Integer> getByPlayers(@PathVariable(value = "id") int id) throws InterruptedException {
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

    @GetMapping("/get")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/pageable")
    public ResponseEntity<?> getTeams(Pageable pageable) {
        return ResponseEntity.ok(teamService.getTeams(pageable));
    }

    @GetMapping("/count")
    public ResponseEntity<List<TeamResponse>> getCount() {
        return ResponseEntity.ok(teamService.getCountById());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Team team) throws DuplicateDataException, BadRequestException {
        teamService.save(team);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveTeam(@RequestBody Team team) {
        teamService.saveTeam(team);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Team team) {
        teamService.update(team);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "id") int id) {
        teamService.edit(name, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") int id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable(value = "id") int id) {
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
