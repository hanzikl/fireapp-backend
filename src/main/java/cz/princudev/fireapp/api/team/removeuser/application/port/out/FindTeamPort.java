package cz.princudev.fireapp.api.team.removeuser.application.port.out;

import cz.princudev.fireapp.api.team.removeuser.domain.TeamState;
import cz.princudev.fireapp.api.team.removeuser.domain.UserState;

public interface FindTeamPort {

    TeamState findTeamById(Long teamId);

    TeamState findTeamByUser(UserState user);
}
