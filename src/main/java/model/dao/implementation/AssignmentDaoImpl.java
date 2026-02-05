package model.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.dao.AssignmentDao;
import model.entity.Assignment;

import java.util.List;

public class AssignmentDaoImpl implements AssignmentDao {

    @Override
    public void create(Assignment assignment) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
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
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            return em.find(Assignment.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Assignment> findAll() {
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            List<Assignment> assignments = em.createQuery("select a from Assignment a").getResultList();
            return assignments;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Assignment> findByCourseId(Integer courseId) {
        EntityManager em = datasource.MariaDbConnection.getInstance();
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
        EntityManager em = datasource.MariaDbConnection.getInstance();
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
        EntityManager em = datasource.MariaDbConnection.getInstance();
        try {
            em.getTransaction().begin();
            em.remove(findById(id));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
