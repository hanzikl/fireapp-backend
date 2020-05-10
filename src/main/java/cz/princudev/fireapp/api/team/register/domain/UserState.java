package cz.princudev.fireapp.api.team.register.domain;

import java.util.Objects;

public interface UserState {

    Long getId();

    String getName();

    default boolean isSame(UserState another) {
        return another != null
                && Objects.equals(this.getId(), another.getId())
                && Objects.equals(this.getName(), another.getName());
    }
}
