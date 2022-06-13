package com.vfa.controller;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Address;
import com.vfa.service.interfaces.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @GetMapping("/city/{name}")
    public ResponseEntity<?> getByCity(@PathVariable(value = "name") String name) throws NotFoundException {
        return ResponseEntity.ok(addressService.getCity(name));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Address address) throws DuplicateDataException, BadRequestException {
        addressService.save(address);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Address address) {
        addressService.update(address);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }


}
