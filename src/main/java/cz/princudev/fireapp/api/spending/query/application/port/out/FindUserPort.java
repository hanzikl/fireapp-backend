package cz.princudev.fireapp.api.spending.query.application.port.out;

import cz.princudev.fireapp.api.spending.query.domain.UserState;

public interface FindUserPort {

    UserState findById(Long id);

}
