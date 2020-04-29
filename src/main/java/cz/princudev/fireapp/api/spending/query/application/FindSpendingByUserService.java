package cz.princudev.fireapp.api.spending.query.application;


import cz.princudev.fireapp.api.spending.query.application.port.in.FindSpendingByUserQuery;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindSpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindUserPort;
import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FindSpendingByUserService implements FindSpendingByUserQuery {

    private final FindSpendingByUserPort findSpendingByUserPort;
    private final FindUserPort findUserPort;

    public FindSpendingByUserService(FindSpendingByUserPort findSpendingByUserPort,
                                     FindUserPort findUserPort) {

        this.findSpendingByUserPort = findSpendingByUserPort;
        this.findUserPort = findUserPort;
    }


    @Override
    public List<SpendingState> findSpendingByUser(Long userId) {

        UserState user = findUserPort.findById(userId);

        if (user == null) {
            throw new IllegalStateException("user not found");
        }

        return findSpendingByUserPort.findAllByUser(user);
    }
}
