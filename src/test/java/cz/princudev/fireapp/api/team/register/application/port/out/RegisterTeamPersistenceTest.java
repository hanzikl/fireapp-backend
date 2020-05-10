package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.persistence.config.JpaConfig;
import cz.princudev.fireapp.api.team.register.domain.TeamState;
import cz.princudev.fireapp.api.team.register.domain.UserState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class RegisterTeamPersistenceTest {

    @Resource(name = "team.register.UserRepo")
    private UserRepo userRepo;

    @Resource(name = "team.register.TeamRepo")
    private TeamRepo teamRepo;

    @Test
    public void test_saveUser() {

        // FIXME: this should be initialized with sql, not with repo
        UserState testUser = new TestUser(1L, "Shakira");
        userRepo.save(UserMapper.mapToEntity(testUser));

        // persist new team
        TeamState newTeam = new TestTeam(null, Collections.singleton(testUser));

        DbTeam persistedTeam = teamRepo.save(TeamMapper.mapToEntity(newTeam));
        Assertions.assertNotNull(persistedTeam);
        Assertions.assertNotNull(persistedTeam.getId());

        // check that it is the same
        Optional<DbTeam> sameTeamOpt = teamRepo.findById(persistedTeam.getId());
        Assertions.assertTrue(sameTeamOpt.isPresent());
        Assertions.assertTrue(
                CollectionUtils.getOnlyElement(sameTeamOpt.get().getUsers()).isSame(testUser));

    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Getter
    private static class TestUser implements UserState {
        private final Long id;
        private final String name;
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Getter
    private static class TestTeam implements TeamState {
        private final Long id;
        private final Set<UserState> users;
    }

}
