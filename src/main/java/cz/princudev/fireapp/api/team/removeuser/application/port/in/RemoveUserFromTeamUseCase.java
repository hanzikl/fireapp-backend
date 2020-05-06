package cz.princudev.fireapp.api.team.removeuser.application.port.in;

import cz.princudev.fireapp.api.team.removeuser.domain.TeamState;

public interface RemoveUserFromTeamUseCase {

    TeamState removeUserFromTeam(RemoveUserFromTeamCommand command);

}
