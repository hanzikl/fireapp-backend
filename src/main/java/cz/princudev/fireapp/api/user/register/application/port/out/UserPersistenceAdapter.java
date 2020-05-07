package cz.princudev.fireapp.api.user.register.application.port.out;

import cz.princudev.fireapp.api.user.register.domain.UserState;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements PersistUserPort {

    private final UserRepo userRepo;

    public UserPersistenceAdapter(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserState persistUser(UserState userState) {
        return userRepo.save(UserMapper.mapToEntity(userState));
    }
}
