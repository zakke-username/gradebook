package model.dao;

import model.entity.Assignment;

import java.util.List;

public interface AssignmentDao {

    void create(Assignment assignment);

    Assignment findById(Integer id);

    List<Assignment> findAll();

    List<Assignment> findByCourseId(Integer courseId);

    void update(Assignment assignment);

    void delete(Integer id);
}
