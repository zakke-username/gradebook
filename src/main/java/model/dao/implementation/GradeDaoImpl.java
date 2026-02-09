package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.dao.GradeDao;
import model.entity.Grade;

import java.util.List;

public class GradeDaoImpl implements GradeDao {

    @Override
    public void create(Grade grade) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(grade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Grade findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            return em.find(Grade.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Grade> findAll() {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            TypedQuery<Grade> query =
                    em.createQuery("SELECT g FROM Grade g", Grade.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Grade> findByStudentId(Integer studentId) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            TypedQuery<Grade> query =
                    em.createQuery(
                            "SELECT g FROM Grade g WHERE g.student.id = :studentId",
                            Grade.class
                    );
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Grade> findByAssignmentId(Integer assignmentId) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            TypedQuery<Grade> query =
                    em.createQuery(
                            "SELECT g FROM Grade g WHERE g.assignment.id = :assignmentId",
                            Grade.class
                    );
            query.setParameter("assignmentId", assignmentId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Grade findByStudentAndAssignment(Integer studentId, Integer assignmentId) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            TypedQuery<Grade> query =
                    em.createQuery(
                            "SELECT g FROM Grade g WHERE g.student.id = :studentId AND g.assignment.id = :assignmentId",
                            Grade.class
                    );
            query.setParameter("studentId", studentId);
            query.setParameter("assignmentId", assignmentId);
            List<Grade> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Grade grade) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(grade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            em.getTransaction().begin();
            Grade grade = em.find(Grade.class, id);
            if (grade != null) {
                em.remove(grade);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
