package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.TeacherDao;
import model.entity.Teacher;

import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    @Override
    public void create(Teacher teacher) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Teacher findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.find(Teacher.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Teacher> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Teacher> query =
                    em.createQuery("SELECT t FROM Teacher t", Teacher.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Teacher> findByLastName(String lastName) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Teacher> query =
                    em.createQuery(
                            "SELECT t FROM Teacher t WHERE t.lastName = :lastName",
                            Teacher.class
                    );
            query.setParameter("lastName", lastName);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Teacher teacher) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(teacher);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher != null) {
                em.remove(teacher);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

