package com.vfa.service.interfaces;

import com.vfa.exception.NotFoundException;
import com.vfa.model.Address;

import java.util.List;

public interface AddressService extends AbstractService<Address>{

    List<Address> getCity(String name) throws NotFoundException;

}
