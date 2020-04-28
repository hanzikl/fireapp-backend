package cz.princudev.fireapp.api.spending.add.application.port.out;

import cz.princudev.fireapp.api.spending.add.domain.SpendingState;

public interface PersistSpendingPort {

    SpendingState persistSpending(SpendingState spendingState);

}
