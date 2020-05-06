package cz.princudev.fireapp.api.team.removeuser.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
