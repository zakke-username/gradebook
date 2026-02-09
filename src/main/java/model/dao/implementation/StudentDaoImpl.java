package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.StudentDao;
import model.entity.Student;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public void create(Student student) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Student findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Student> query =
                    em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findByEnrollmentYear(Integer year) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            TypedQuery<Student> query =
                    em.createQuery(
                            "SELECT s FROM Student s WHERE s.enrollmentYear = :year",
                            Student.class
                    );
            query.setParameter("year", year);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Student student) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(student);
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
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
