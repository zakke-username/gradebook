package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.EnrollmentDao;
import model.entity.Enrollment;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {

    @Override
    public void create(Enrollment enrollment) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(enrollment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Enrollment findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.find(Enrollment.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Enrollment> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Enrollment> query =
                    em.createQuery("SELECT e FROM Enrollment e", Enrollment.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Enrollment> findByStudentId(Integer studentId) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Enrollment> query =
                    em.createQuery(
                            "SELECT e FROM Enrollment e WHERE e.student.id = :studentId",
                            Enrollment.class
                    );
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Enrollment> findByCourseId(Integer courseId) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Enrollment> query =
                    em.createQuery(
                            "SELECT e FROM Enrollment e WHERE e.course.id = :courseId",
                            Enrollment.class
                    );
            query.setParameter("courseId", courseId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Enrollment enrollment) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(enrollment);
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
            Enrollment enrollment = em.find(Enrollment.class, id);
            if (enrollment != null) {
                em.remove(enrollment);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
