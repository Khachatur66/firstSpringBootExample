package com.vfa.service.interfaces;

import com.vfa.dto.request.CoachRequest;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoachService extends AbstractService<Coach> {

    List<Coach> getCoachExperience(int from , int to);

    Page<Coach> getByTeamId(int id, Pageable pageable);

    void updateDTO(int id, CoachRequest coachRequest) throws NotFoundException;


//    void updateTeam(Team team, int id);

}
