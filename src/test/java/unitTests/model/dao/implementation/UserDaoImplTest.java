package unitTests.model.dao.implementation;

import datasource.MariaDbConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.dao.implementation.UserDaoImpl;
import model.entity.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao;
    private EntityManager em;
    private EntityTransaction transaction;

    private final List<Integer> createdUserIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        em = MariaDbConnection.getEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
    }

    @AfterEach
    void tearDown() {
        if (transaction.isActive()) {
            transaction.rollback();
        }
        for (Integer id : createdUserIds) {
            userDao.delete(id);
        }
        createdUserIds.clear();
        em.close();
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private User buildUser(String username, String passwordHash, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        return user;
    }

    private User createTracked(String username, String passwordHash, String role) {
        User user = buildUser(username, passwordHash, role);
        userDao.create(user);
        createdUserIds.add(user.getId());
        return user;
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    void create_ShouldPersistUserToDatabase() {
        User user = createTracked("alice", "hash123", "STUDENT");

        // Re-fetch from DB to confirm it was persisted
        User found = em.find(User.class, user.getId());
        assertNotNull(found);
        assertEquals("alice", found.getUsername());
        assertEquals("STUDENT", found.getRole());
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    void update_ShouldModifyExistingUser() {
        User user = createTracked("bob", "oldHash", "STUDENT");
        int id = user.getId();

        user.setPasswordHash("newHash");
        user.setRole("TEACHER");
        userDao.update(user);

        em.clear(); // clear cache to force re-fetch from DB
        User updated = em.find(User.class, id);
        assertEquals("newHash", updated.getPasswordHash());
        assertEquals("TEACHER", updated.getRole());
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    void delete_WhenUserExists_ShouldRemoveFromDatabase() {
        User user = createTracked("charlie", "hash123", "STUDENT");
        int id = user.getId();

        userDao.delete(id);

        em.clear();
        User deleted = em.find(User.class, id);
        assertNull(deleted);
    }

    @Test
    void delete_WhenUserDoesNotExist_ShouldNotThrow() {
        assertDoesNotThrow(() -> userDao.delete(99999));
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    void findById_WhenUserExists_ShouldReturnCorrectUser() {
        User user = createTracked("diana", "hash123", "TEACHER");

        User found = userDao.findById(user.getId());

        assertNotNull(found);
        assertEquals("diana", found.getUsername());
        assertEquals("TEACHER", found.getRole());
    }

    @Test
    void findById_WhenUserDoesNotExist_ShouldReturnNull() {
        User found = userDao.findById(99999);
        assertNull(found);
    }

    // ── findByUsername ────────────────────────────────────────────────────────

    @Test
    void findByUsername_WhenUserExists_ShouldReturnCorrectUser() {
        User user = createTracked("eve", "hash123", "STUDENT");

        User found = userDao.findByUsername("eve");

        assertNotNull(found);
        assertEquals("eve", found.getUsername());
    }

    @Test
    void findByUsername_WhenUserDoesNotExist_ShouldThrowNoResultException() {
        // getSingleResult() throws NoResultException when nothing is found —
        // you may want to catch this in the impl and return null instead
        assertThrows(Exception.class, () -> userDao.findByUsername("ghost"));
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    void findAll_ShouldIncludePersistedUsers() {
        createTracked("frank", "hash1", "STUDENT");
        createTracked("grace", "hash2", "TEACHER");

        List<User> users = userDao.findAll();

        // Check at least our two users are present (DB may have pre-existing rows)
        long count = users.stream()
                .filter(u -> u.getUsername().equals("frank") || u.getUsername().equals("grace"))
                .count();
        assertEquals(2, count);
    }

    @Test
    void findAll_ShouldReturnNonEmptyList() {
        createTracked("henry", "hash123", "STUDENT");

        List<User> users = userDao.findAll();

        assertFalse(users.isEmpty());
    }
}