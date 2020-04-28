package cz.princudev.fireapp.api.spending.query;


import cz.princudev.fireapp.api.spending.query.application.port.in.QuerySpendingByUserCommand;
import cz.princudev.fireapp.api.spending.query.application.port.in.FindSpendingByUserQuery;
import cz.princudev.fireapp.api.spending.query.application.port.out.QuerySpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.QueryUserPort;
import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FindSpendingByUserService implements FindSpendingByUserQuery {

    private final QuerySpendingByUserPort querySpendingByUserPort;
    private final QueryUserPort queryUserPort;

    public FindSpendingByUserService(QuerySpendingByUserPort querySpendingByUserPort,
                                     QueryUserPort queryUserPort) {

        this.querySpendingByUserPort = querySpendingByUserPort;
        this.queryUserPort = queryUserPort;
    }


    @Override
    public List<SpendingState> findSpendingByUser(QuerySpendingByUserCommand command) {

        UserState user = queryUserPort.findById(command.getUserId());

        if (user == null) {
            throw new IllegalStateException("user not found");
        }

        return querySpendingByUserPort.findAllByUser(user);
    }
}
