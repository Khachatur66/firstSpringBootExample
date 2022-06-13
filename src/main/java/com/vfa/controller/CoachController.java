package com.vfa.controller;

import com.vfa.dto.request.CoachRequestDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Coach;
import com.vfa.service.interfaces.CoachService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coach")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(coachService.getById(id));
    }

    @GetMapping("/experience")
    public ResponseEntity<?> getByCoachExperience(int from, int to) {
        return ResponseEntity.ok(coachService.getCoachExperience(from, to));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<?> getByTeamId(@PathVariable(value = "id") int id,
                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(coachService.getByTeamId(id, pageable));
    }

    @GetMapping
    public ResponseEntity<Void> getAll() {
        coachService.getAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Coach coach) throws DuplicateDataException, BadRequestException {
        coachService.save(coach);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Coach coach) {
        coachService.update(coach);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCoachDTO(@PathVariable(value = "id") int id, @RequestBody CoachRequestDTO coachRequestDTO) throws NotFoundException {
        coachService.updateDTO(id, coachRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        coachService.delete(id);
        return ResponseEntity.ok().build();
    }
}
