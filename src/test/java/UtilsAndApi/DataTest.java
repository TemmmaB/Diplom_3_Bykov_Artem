package UtilsAndApi;

import java.util.UUID;

public class DataTest {
    private final String name;
    private final String email;
    private final String password;

    public DataTest() {
        this.name = "TestUser" + UUID.randomUUID();
        this.email = "emailexampleuser" + UUID.randomUUID() + "@ya.ru";
        this.password = "TestPassword@2025" + UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
