package cz.princudev.fireapp.api.team.removeuser.application.port.out;


import cz.princudev.fireapp.api.team.removeuser.domain.TeamState;

public interface PersistTeamPort {

    TeamState persistTeam(TeamState team);

    void deleteTeam(TeamState team);

}
