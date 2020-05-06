package cz.princudev.fireapp.api.team.adduser.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
