package com.vfa.service.implemenation;

import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Authority;
import com.vfa.repository.AuthorityRepository;
import com.vfa.service.interfaces.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getById(int id) throws NotFoundException {
        return authorityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Authority with current ID: " + id));
    }

    @Override
    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Authority authority) throws DuplicateDataException, BadRequestException {
        authorityRepository.save(authority);
    }

    @Transactional
    @Override
    public void update(Authority authority) {
        authorityRepository.save(authority);
    }

    @Transactional
    @Override
    public void delete(int id) {
        authorityRepository.deleteById(id);
    }

}
