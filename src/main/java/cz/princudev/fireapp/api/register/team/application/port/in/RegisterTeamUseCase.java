package cz.princudev.fireapp.api.register.team.application.port.in;

import cz.princudev.fireapp.api.register.team.domain.TeamState;

public interface RegisterTeamUseCase {

    TeamState registerTeam(RegisterTeamCommand command);

}
