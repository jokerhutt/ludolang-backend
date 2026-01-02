package support;

import org.testcontainers.containers.MySQLContainer;

public final class Containers {
    public static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.0.39");

    static { MYSQL.start(); }

    private Containers() {}
}