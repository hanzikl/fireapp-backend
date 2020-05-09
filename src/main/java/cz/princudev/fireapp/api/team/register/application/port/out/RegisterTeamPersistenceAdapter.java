package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.team.register.domain.TeamState;
import cz.princudev.fireapp.api.team.register.domain.UserState;
import org.springframework.stereotype.Component;

@Component
public class RegisterTeamPersistenceAdapter implements FindUserAndTeamPort, PersistTeamPort {

    private final UserRepo userRepo;
    private final TeamRepo teamRepo;

    public RegisterTeamPersistenceAdapter(UserRepo userRepo, TeamRepo teamRepo) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
    }

    @Override
    public TeamState findUserTeam(UserState user) {
        return userRepo.findById(user.getId()).map(DbUser::getTeam).orElse(null);
    }

    @Override
    public UserState findUser(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public TeamState persistTeam(TeamState teamState) {
        return teamRepo.save(TeamMapper.mapToEntity(teamState));
    }
}
