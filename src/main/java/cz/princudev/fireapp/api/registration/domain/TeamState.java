package cz.princudev.fireapp.api.registration.domain;

import java.util.Set;

public interface TeamState {

    Long getId();

    Set<UserState> getUsers();

}
