package cz.princudev.fireapp.api.team.register.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
