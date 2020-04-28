package cz.princudev.fireapp.api.spending.query.application.port.in;


import cz.princudev.fireapp.api.spending.query.domain.SpendingState;

import java.util.List;

public interface FindSpendingByUserQuery {

    List<SpendingState> findSpendingByUser(QuerySpendingByUserCommand command);

}
