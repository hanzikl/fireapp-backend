package cz.princudev.fireapp.api.registration.application.port.in;

import cz.princudev.fireapp.api.registration.domain.TeamState;

public interface RegisterTeamUseCase {

    TeamState registerTeam(RegisterTeamCommand command);

}
