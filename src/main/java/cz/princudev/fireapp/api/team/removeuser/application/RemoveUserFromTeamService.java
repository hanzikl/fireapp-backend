package cz.princudev.fireapp.api.team.removeuser.application;

import cz.princudev.fireapp.api.team.removeuser.application.port.in.RemoveUserFromTeamCommand;
import cz.princudev.fireapp.api.team.removeuser.application.port.in.RemoveUserFromTeamUseCase;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.FindUserPort;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.team.removeuser.domain.TeamState;
import cz.princudev.fireapp.api.team.removeuser.domain.UserState;

public class RemoveUserFromTeamService implements RemoveUserFromTeamUseCase {

    private final PersistTeamPort persistTeamPort;

    private final FindTeamPort findTeamPort;

    private final FindUserPort findUserPort;

    public RemoveUserFromTeamService(PersistTeamPort persistTeamPort,
                                     FindTeamPort findTeamPort,
                                     FindUserPort findUserPort) {
        this.persistTeamPort = persistTeamPort;
        this.findTeamPort = findTeamPort;
        this.findUserPort = findUserPort;
    }

    @Override
    public TeamState removeUserFromTeam(RemoveUserFromTeamCommand command) {

        TeamState team = findTeamPort.findTeamById(command.getTeamId());
        UserState user = findUserPort.findUserById(command.getUserId());

        validateCurrentState(team, user);

        team.getUsers().remove(user);

        if (team.getUsers().isEmpty()) {
            persistTeamPort.deleteTeam(team);
            return null;
        } else {
            return persistTeamPort.persistTeam(team);
        }

    }

    private void validateCurrentState(TeamState team, UserState user) {
        if (team == null) {
            throw new IllegalStateException("team does not exists");
        }

        if (user == null) {
            throw new IllegalStateException("user does not exists");
        }

        if (!team.getUsers().contains(user)) {
            throw new IllegalStateException("user is not present in given team");
        }

    }
}
