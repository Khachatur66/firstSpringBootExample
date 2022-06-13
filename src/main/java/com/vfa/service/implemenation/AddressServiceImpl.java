package com.vfa.service.implemenation;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Address;
import com.vfa.repository.AddressRepository;
import com.vfa.service.interfaces.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getById(int id) throws NotFoundException {
        return addressRepository
                .getByAddressId(id)
                .orElseThrow(() -> new NotFoundException("could not find address with current id: " + id));
    }

    @Override
    public List<Address> getCity(String name) throws NotFoundException {
        return addressRepository
                .getByAddressCity(name)
                .orElseThrow(() -> new NotFoundException("could not find street with current name: " + name));
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public void save(Address address) throws DuplicateDataException, BadRequestException {

        String building = address.getStreet();

        if (addressRepository.findByBuilding(building) != null) {
            throw new DuplicateDataException(building + " building already exist");
        }

        String street = address.getStreet();

        int count = addressRepository.countByStreet(street);
        if (count > 2) {
            throw new BadRequestException("the Number of " + street + " should not exceed 3");
        }

        addressRepository.save(address);
    }

    @Override
    public void update(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void delete(int id) {
        addressRepository.deleteById(id);
    }

}
