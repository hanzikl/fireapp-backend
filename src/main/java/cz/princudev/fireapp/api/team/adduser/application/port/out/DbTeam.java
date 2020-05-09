package cz.princudev.fireapp.api.team.adduser.application.port.out;

import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import cz.princudev.fireapp.api.team.adduser.domain.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Entity(name = "team.adduser.DbTeam")
@Table(name = "teams")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
class DbTeam implements TeamState {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @OneToMany
    @Setter
    private Set<DbUser> users;

    @Override
    public Set<UserState> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}
