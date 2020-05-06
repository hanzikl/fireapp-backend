package cz.princudev.fireapp.api.team.adduser.application.port.in;

import cz.princudev.fireapp.api.team.adduser.domain.TeamState;

public interface AddUserToTeamUseCase {

    TeamState addUserToTeam(AddUserToTeamCommand command);

}
