package cz.princudev.fireapp.api.spending.query.application.port.out;

import cz.princudev.fireapp.api.spending.query.domain.TeamState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;

import java.util.Collection;

public interface FindUserByTeamPort {

    Collection<UserState> findUsersByTeam(TeamState team);


}
