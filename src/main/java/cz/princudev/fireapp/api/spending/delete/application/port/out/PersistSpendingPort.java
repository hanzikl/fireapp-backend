package cz.princudev.fireapp.api.spending.delete.application.port.out;

public interface PersistSpendingPort {

    void deleteSpending(Long spendingId);

}
