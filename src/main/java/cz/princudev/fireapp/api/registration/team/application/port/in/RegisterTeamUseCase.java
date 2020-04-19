package cz.princudev.fireapp.api.registration.team.application.port.in;

import cz.princudev.fireapp.api.registration.team.domain.TeamState;

public interface RegisterTeamUseCase {

    TeamState registerTeam(RegisterTeamCommand command);

}
