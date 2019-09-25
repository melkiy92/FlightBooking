package academy.softserve.flightbooking.models;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"),
    ADMIN("USER", "ADMIN");

    private ImmutableList<String> authorities;

    UserRole(String... authorities) {
        this.authorities = ImmutableList.copyOf(authorities);
    }
}