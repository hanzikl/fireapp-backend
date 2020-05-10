package cz.princudev.fireapp.api.team.register.application.port.out;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("team.register.TeamRepo")
interface TeamRepo extends CrudRepository<DbTeam, Long> {


}