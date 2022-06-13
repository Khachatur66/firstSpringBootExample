package com.vfa.service.interfaces;

import com.vfa.dto.request.RefereeRequestDTO;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Referee;

import java.util.List;

public interface RefereeService extends AbstractService<Referee>{

    void updateDTO(int id, RefereeRequestDTO refereeRequestDTO) throws NotFoundException;

    List<Referee> getExperience(int from, int to);
}
