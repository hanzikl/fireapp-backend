package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.register.team.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistTeam(TeamState userState);

}
