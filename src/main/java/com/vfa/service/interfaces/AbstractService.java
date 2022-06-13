package com.vfa.service.interfaces;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;

import java.util.List;

public interface AbstractService<T> {

    void save(T t) throws DuplicateDataException, BadRequestException;

    void update(T t);

    void delete(int id);

    T getById(int id) throws NotFoundException;

    List<T> getAll();
}
