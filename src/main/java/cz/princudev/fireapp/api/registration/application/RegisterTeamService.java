package cz.princudev.fireapp.api.registration.application;

import cz.princudev.fireapp.api.registration.application.port.in.RegisterTeamCommand;
import cz.princudev.fireapp.api.registration.application.port.in.RegisterTeamUseCase;
import cz.princudev.fireapp.api.registration.domain.TeamState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class RegisterTeamService implements RegisterTeamUseCase {

    @Override
    public TeamState registerTeam(RegisterTeamCommand command) {
        throw new UnsupportedOperationException("not implemented");
    }

}
