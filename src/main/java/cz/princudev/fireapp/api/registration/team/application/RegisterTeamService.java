package cz.princudev.fireapp.api.registration.team.application;

import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamCommand;
import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamUseCase;
import cz.princudev.fireapp.api.registration.team.application.port.out.FindUserAndTeamPort;
import cz.princudev.fireapp.api.registration.team.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.registration.team.domain.Team;
import cz.princudev.fireapp.api.registration.team.domain.TeamState;
import cz.princudev.fireapp.api.registration.team.domain.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
class RegisterTeamService implements RegisterTeamUseCase {

    private final FindUserAndTeamPort findUserAndTeamPort;
    private final PersistTeamPort persistTeamPort;

    public RegisterTeamService(FindUserAndTeamPort findUserAndTeamPort, PersistTeamPort persistTeamPort) {
        this.findUserAndTeamPort = findUserAndTeamPort;
        this.persistTeamPort = persistTeamPort;
    }

    @Override
    public TeamState registerTeam(RegisterTeamCommand command) {

        UserState user = findUserAndTeamPort.findUser(command.getInitialUserId());

        if (user == null) {
            throw new IllegalStateException(String.format("user #%d not found", command.getInitialUserId()));
        }

        TeamState existingTeam = findUserAndTeamPort.findUserTeam(user);

        if (existingTeam != null) {
            throw new IllegalStateException(String.format("user #%d already has a team", command.getInitialUserId()));
        }

        return createNewTeamWithOneMember(user);
    }

    private TeamState createNewTeamWithOneMember(UserState user) {
        Team team = Team.builder()
                .userSet(Collections.singleton(user))
                .build();

        return persistTeamPort.persistTeam(team);
    }

}
