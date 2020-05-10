package cz.princudev.fireapp.api.user.register.application.port.out;

import cz.princudev.fireapp.api.persistence.config.JpaConfig;
import cz.princudev.fireapp.api.user.register.domain.User;
import cz.princudev.fireapp.api.user.register.domain.UserState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class RegisterUserPersistenceTest {

    @Resource
    private UserRepo userRepo;

    @Test
    public void test_saveUser() {

        UserState user = new User(null, "Karlos");

        DbUser persistedEntity = userRepo.save(UserMapper.mapToEntity(user));
        Assertions.assertNotNull(persistedEntity);
        Assertions.assertNotNull(persistedEntity.getId());

        Optional<DbUser> persistedUser = userRepo.findById(persistedEntity.getId());

        Assertions.assertTrue(persistedUser.isPresent());
        Assertions.assertEquals("Karlos", persistedUser.get().getName());
    }

}
