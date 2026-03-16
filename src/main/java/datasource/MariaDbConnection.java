package datasource;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

public class MariaDbConnection {

    private static final EntityManagerFactory emf;

    static {
        String host     = System.getenv("DB_HOST");
        String port     = System.getenv("DB_PORT");
        String name     = System.getenv("DB_NAME");
        String user     = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (host     == null || host.isEmpty())     host     = "localhost";
        if (port     == null || port.isEmpty())     port     = "3306";
        if (name     == null || name.isEmpty())     name     = "gradebook";
        if (user     == null || user.isEmpty())     user     = "appuser";
        if (password == null || password.isEmpty()) password = "password";

        Map<String, Object> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://" + host + ":" + port + "/" + name +
                        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        props.put("jakarta.persistence.jdbc.user", user);
        props.put("jakarta.persistence.jdbc.password", password);

        emf = Persistence.createEntityManagerFactory("GradebookMariaDbUnit", props);
    }

    private MariaDbConnection() {
        // prevent instantiation
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}