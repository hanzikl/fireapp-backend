package cz.princudev.fireapp.api.spending.query.application;

import cz.princudev.fireapp.api.spending.query.application.port.in.FindSpendingByTeamQuery;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindSpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindUserByTeamPort;
import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
import cz.princudev.fireapp.api.spending.query.domain.TeamState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FindSpendingByTeamService implements FindSpendingByTeamQuery {

    private final FindUserByTeamPort findUserByTeamPort;
    private final FindSpendingByUserPort findSpendingByUserPort;
    private final FindTeamPort findTeamPort;

    public FindSpendingByTeamService(FindUserByTeamPort findUserByTeamPort,
                                     FindSpendingByUserPort findSpendingByUserPort,
                                     FindTeamPort findTeamPort) {
        this.findUserByTeamPort = findUserByTeamPort;
        this.findSpendingByUserPort = findSpendingByUserPort;
        this.findTeamPort = findTeamPort;
    }

    @Override
    public List<SpendingState> findSpendingByTeam(Long teamId) {

        TeamState team = findTeamPort.findById(teamId);
        if (team == null)
            throw new IllegalStateException("Team not found");

        Collection<UserState> users = findUserByTeamPort.findUsersByTeam(team);

        return users.stream()
                .flatMap(user -> findSpendingByUserPort.findAllByUser(user).stream())
                .collect(Collectors.toList());
    }

}
