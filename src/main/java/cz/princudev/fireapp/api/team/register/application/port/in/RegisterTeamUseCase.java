package cz.princudev.fireapp.api.team.register.application.port.in;

import cz.princudev.fireapp.api.team.register.domain.TeamState;

public interface RegisterTeamUseCase {

    TeamState registerTeam(RegisterTeamCommand command);

}
