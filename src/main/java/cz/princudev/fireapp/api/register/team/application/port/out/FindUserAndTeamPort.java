package cz.princudev.fireapp.api.register.team.application.port.out;

import cz.princudev.fireapp.api.register.team.domain.TeamState;
import cz.princudev.fireapp.api.register.team.domain.UserState;

public interface FindUserAndTeamPort {

    TeamState findUserTeam(UserState user);

    UserState findUser(Long userId);

}
