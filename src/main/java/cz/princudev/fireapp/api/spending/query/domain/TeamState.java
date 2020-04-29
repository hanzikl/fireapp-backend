package cz.princudev.fireapp.api.spending.query.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
