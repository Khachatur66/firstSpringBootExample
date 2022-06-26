package com.vfa.controller;

import com.vfa.dto.request.RefereeRequest;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Referee;
import com.vfa.service.interfaces.RefereeService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/referee")
public class RefereeController {

    private final RefereeService refereeService;

    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(refereeService.getById(id));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getInfoById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(refereeService.getRefereeInfo(id));
    }

    @GetMapping("/experience")
    public ResponseEntity<?> getExperience(int from, int to) {
        return ResponseEntity.ok(refereeService.getExperience(from, to));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(refereeService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Referee referee) throws DuplicateDataException, BadRequestException {
        refereeService.save(referee);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Referee referee) {
        refereeService.update(referee);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDTO(@PathVariable(value = "id") int id,
                                          @RequestBody RefereeRequest refereeRequest) throws NotFoundException {
        refereeService.updateDTO(id, refereeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        refereeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
