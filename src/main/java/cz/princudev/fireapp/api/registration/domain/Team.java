package cz.princudev.fireapp.api.registration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class Team implements TeamState {

    private Long id;

    private Set<User> userSet;

    public Set<UserState> getUsers() {
        return new HashSet<>(userSet);
    }

}
