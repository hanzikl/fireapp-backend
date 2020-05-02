package cz.princudev.fireapp.api.team.adduser.application.port.out;

import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import cz.princudev.fireapp.api.team.adduser.domain.UserState;

public interface FindTeamPort {

    TeamState findTeamById(Long teamId);

    TeamState findTeamByUser(UserState user);
}
