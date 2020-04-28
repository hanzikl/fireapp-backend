package cz.princudev.fireapp.api.spending.update.application.port.out;

import cz.princudev.fireapp.api.spending.update.domain.SpendingState;

public interface PersistSpendingPort {

    SpendingState persistSpending(SpendingState spendingState);

    SpendingState findSpendingById(Long spendingId);

}
