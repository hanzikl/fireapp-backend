package cz.princudev.fireapp.api.team.adduser.application.port.out;

import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import cz.princudev.fireapp.api.team.adduser.domain.UserState;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AddUserToTeamPersistenceAdapter implements FindTeamPort, FindUserPort, PersistTeamPort {

    private final UserRepo userRepo;
    private final TeamRepo teamRepo;

    AddUserToTeamPersistenceAdapter(UserRepo userRepo, TeamRepo teamRepo) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
    }

    @Override
    public TeamState findTeamById(Long teamId) {
        return teamRepo.findById(teamId).orElse(null);
    }

    @Override
    public TeamState findTeamByUser(UserState user) {
        Optional<DbUser> userOpt = userRepo.findById(user.getId());
        return userOpt.map(DbUser::getTeam).orElse(null);
    }

    @Override
    public UserState findUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public TeamState persistTeam(TeamState teamState) {
        return teamRepo.save(TeamMapper.mapToEntity(teamState));
    }
}
