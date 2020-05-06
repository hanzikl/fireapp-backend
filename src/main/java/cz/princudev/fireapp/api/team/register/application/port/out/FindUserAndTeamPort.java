package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.team.register.domain.TeamState;
import cz.princudev.fireapp.api.team.register.domain.UserState;

public interface FindUserAndTeamPort {

    TeamState findUserTeam(UserState user);

    UserState findUser(Long userId);

}
