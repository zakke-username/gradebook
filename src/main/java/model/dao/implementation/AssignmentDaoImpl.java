package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.dao.AssignmentDao;
import model.entity.Assignment;
import model.entity.Student;

import java.util.List;

public class AssignmentDaoImpl implements AssignmentDao {

    @Override
    public void create(Assignment assignment) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(assignment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @Override
    public Assignment findById(Integer id) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            return em.find(Assignment.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Assignment> findAll() {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            List<Assignment> assignments = em.createQuery("select a from Assignment a").getResultList();
            return assignments;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Assignment> findByCourseId(Integer courseId) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            Query q = em.createQuery("select a from Assignment a where a.course.id = :courseId");
            q.setParameter("courseId", courseId);
            List<Assignment> list = q.getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Assignment assignment) {
        EntityManager em = datasource.MariaDbConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(assignment);
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
            Assignment assignment = em.find(Assignment.class, id);
            if (assignment != null) {
                em.remove(assignment);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
