package cz.princudev.fireapp.api.registration.team.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class Team implements TeamState {

    private Long id;

    private Set<UserState> userSet;

    @Override
    public Set<UserState> getUsers() {
        return new HashSet<>(userSet);
    }

}
