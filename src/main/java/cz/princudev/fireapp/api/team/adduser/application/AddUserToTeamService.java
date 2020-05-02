package cz.princudev.fireapp.api.team.adduser.application;

import cz.princudev.fireapp.api.team.adduser.application.port.in.AddUserToTeamCommand;
import cz.princudev.fireapp.api.team.adduser.application.port.in.AddUserToTeamUseCase;
import cz.princudev.fireapp.api.team.adduser.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.team.adduser.application.port.out.FindUserPort;
import cz.princudev.fireapp.api.team.adduser.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import cz.princudev.fireapp.api.team.adduser.domain.UserState;

public class AddUserToTeamService implements AddUserToTeamUseCase {

    private final PersistTeamPort persistTeamPort;

    private final FindTeamPort findTeamPort;

    private final FindUserPort findUserPort;

    public AddUserToTeamService(PersistTeamPort persistTeamPort,
                                FindTeamPort findTeamPort,
                                FindUserPort findUserPort) {
        this.persistTeamPort = persistTeamPort;
        this.findTeamPort = findTeamPort;
        this.findUserPort = findUserPort;
    }

    @Override
    public TeamState addUserToTeam(AddUserToTeamCommand command) {

        TeamState team = findTeamPort.findTeamById(command.getTeamId());
        UserState user = findUserPort.findUserById(command.getUserId());

        validateCurrentState(team, user);

        team.getUsers().add(user);
        return persistTeamPort.persistTeam(team);

    }

    private void validateCurrentState(TeamState team, UserState user) {
        if (team == null) {
            throw new IllegalStateException("team does not exists");
        }

        if (user == null) {
            throw new IllegalStateException("user does not exists");
        }

        if (team.getUsers().contains(user)) {
            throw new IllegalStateException("user is already in this team");
        }

        TeamState userTeam = findTeamPort.findTeamByUser(user);
        if (userTeam != null) {
            throw new IllegalStateException("user is already in another team");
        }
    }
}
