package cz.princudev.fireapp.api.team.adduser.application.port.out;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("team.addUser.UserRepo")
interface UserRepo extends CrudRepository<DbUser, Long> {

}
