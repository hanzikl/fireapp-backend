package cz.princudev.fireapp.api.spending.delete.application.port.in;


public interface DeleteSpendingUseCase {

    void deleteSpending(DeleteSpendingCommand command);

}
