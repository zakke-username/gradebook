package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.UserDao;
import model.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public void create(User user) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(int id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            User user = em.find(User.class, id);
            return user;
        } finally {
            em.close();
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class
            );
            query.setParameter("username", username);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

//    @Override
//    public Optional<User> findByUsernameAndPassword(String username, String passwordHash) {
//        EntityManager em = datasource.MariaDbConnection.getEntityManager();
//        try {
//            TypedQuery<User> query = em.createQuery(
//                    "SELECT u FROM User u WHERE u.username = :username AND u.passwordHash = :passwordHash",
//                    User.class
//            );
//            query.setParameter("username", username);
//            query.setParameter("passwordHash", passwordHash);
//            return Optional.of(query.getSingleResult());
//        } catch (NoResultException e) {
//            return Optional.empty();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<User> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally {
            em.close();
        }
    }
}