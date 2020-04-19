package cz.princudev.fireapp.api.registration.team.application.port.out;

import cz.princudev.fireapp.api.registration.team.domain.TeamState;
import cz.princudev.fireapp.api.registration.team.domain.UserState;

public interface FindUserAndTeamPort {

    TeamState findUserTeam(UserState user);

    UserState findUser(Long userId);

}
