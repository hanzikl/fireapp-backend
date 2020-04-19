package cz.princudev.fireapp.api.registration.application.port.out;

import cz.princudev.fireapp.api.registration.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistUser(TeamState userState);

}
