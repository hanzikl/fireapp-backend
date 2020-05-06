package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.team.register.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistTeam(TeamState userState);

}
