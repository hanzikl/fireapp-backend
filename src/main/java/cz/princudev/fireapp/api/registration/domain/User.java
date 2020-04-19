package cz.princudev.fireapp.api.registration.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements UserState {

    private final Long id;
    private final String name;

}
