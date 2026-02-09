package datasource;

import jakarta.persistence.*;

public class MariaDbConnection {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GradebookMariaDbUnit");

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