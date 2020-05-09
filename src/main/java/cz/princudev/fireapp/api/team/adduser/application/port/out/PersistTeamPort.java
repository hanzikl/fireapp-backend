package cz.princudev.fireapp.api.team.adduser.application.port.out;


import cz.princudev.fireapp.api.team.adduser.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistTeam(TeamState teamState);

}
