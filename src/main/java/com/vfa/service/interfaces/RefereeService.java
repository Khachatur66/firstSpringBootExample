package com.vfa.service.interfaces;

import com.vfa.dto.request.RefereeRequest;
import com.vfa.dto.response.RefereeResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Referee;

import java.util.List;

public interface RefereeService extends AbstractService<Referee>{

    void updateDTO(int id, RefereeRequest refereeRequest) throws NotFoundException;

    List<Referee> getExperience(int from, int to);

    RefereeResponse getRefereeInfo(int id);

    Object getRefereeById(int id) throws BadRequestException;
}
