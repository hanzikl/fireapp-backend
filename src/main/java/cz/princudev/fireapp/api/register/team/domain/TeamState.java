package cz.princudev.fireapp.api.register.team.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
