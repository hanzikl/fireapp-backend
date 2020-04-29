package cz.princudev.fireapp.api.spending.query.application.port.out;

import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;

import java.util.List;

public interface FindSpendingByUserPort {


    // TODO: we will probably need something more precise - like this:
    // List<SpendingState> findAllByUser(UserState user, Pageable pageable);

    List<SpendingState> findAllByUser(UserState user);

}
