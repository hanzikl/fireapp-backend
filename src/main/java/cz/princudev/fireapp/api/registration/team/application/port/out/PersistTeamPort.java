package cz.princudev.fireapp.api.registration.team.application.port.out;

import cz.princudev.fireapp.api.registration.team.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistTeam(TeamState userState);

}
