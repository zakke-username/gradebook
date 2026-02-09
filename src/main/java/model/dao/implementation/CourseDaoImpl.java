package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.CourseDao;
import model.entity.Course;

import java.util.List;

public class CourseDaoImpl implements CourseDao {

    @Override
    public void create(Course course) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Course findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.find(Course.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Course> query =
                    em.createQuery("SELECT c FROM Course c", Course.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> findByTeacherId(Integer teacherId) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Course> query =
                    em.createQuery(
                            "SELECT c FROM Course c WHERE c.teacher.id = :teacherId",
                            Course.class
                    );
            query.setParameter("teacherId", teacherId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Course course) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(course);
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
            Course course = em.find(Course.class, id);
            if (course != null) {
                em.remove(course);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
