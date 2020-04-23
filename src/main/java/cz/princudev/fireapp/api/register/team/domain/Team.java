package cz.princudev.fireapp.api.register.team.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Team implements TeamState {

    private Long id;

    private Set<UserState> userSet;

    @Override
    public Set<UserState> getUsers() {
        return new HashSet<>(userSet);
    }

}
