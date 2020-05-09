package cz.princudev.fireapp.api.team.adduser.application.port.out;

import org.springframework.data.repository.CrudRepository;

interface TeamRepo extends CrudRepository<DbTeam, Long> {



}
