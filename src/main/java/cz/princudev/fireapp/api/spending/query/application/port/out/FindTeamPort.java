package cz.princudev.fireapp.api.spending.query.application.port.out;

import cz.princudev.fireapp.api.spending.query.domain.TeamState;

public interface FindTeamPort {

    TeamState findById(Long id);

}
