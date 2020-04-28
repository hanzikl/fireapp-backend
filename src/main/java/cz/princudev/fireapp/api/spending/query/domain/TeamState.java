package cz.princudev.fireapp.api.spending.query.domain;

import cz.princudev.fireapp.api.team.register.domain.UserState;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
