package cz.princudev.fireapp.api.user.register.application.port.out;

import org.springframework.data.repository.CrudRepository;

interface UserRepo extends CrudRepository<DbUser, Long> {

}
