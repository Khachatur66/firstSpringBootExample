package com.vfa.controller;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Authority;
import com.vfa.service.interfaces.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authority> getById(@PathVariable("id") int id) throws NotFoundException {
        return ResponseEntity.ok(authorityService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Authority>> getAll() throws NotFoundException {
        return ResponseEntity.ok(authorityService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Authority authority) throws DuplicateDataException, BadRequestException {
        authorityService.save(authority);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Authority authority) throws DuplicateDataException, BadRequestException {
        authorityService.save(authority);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        authorityService.delete(id);
        return ResponseEntity.ok().build();
    }

}
