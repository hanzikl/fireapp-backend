package cz.princudev.fireapp.api.team.adduser.application.port.out;

import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TeamMapper {

    static DbTeam mapToEntity(TeamState teamState) {

        DbTeam team = DbTeam.builder()
                .id(teamState.getId())
                .users(new HashSet<>())
                .build();

        Set<DbUser> teamUsers = teamState.getUsers().stream()
                .map(UserMapper::mapToEntity)
                .collect(Collectors.toSet());

        for (DbUser teamUser : teamUsers) {
            teamUser.setTeam(team);
        }

        team.setUsers(teamUsers);

        return team;
    }

}
