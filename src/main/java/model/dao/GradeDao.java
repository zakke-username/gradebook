package model.dao;

import model.entity.Grade;
import java.util.List;

public interface GradeDao {

    void create(Grade grade);

    Grade findById(Integer id);

    List<Grade> findAll();

    List<Grade> findByStudentId(Integer studentId);

    List<Grade> findByAssignmentId(Integer assignmentId);

    Grade findByStudentAndAssignment(Integer studentId, Integer assignmentId);

    void update(Grade grade);

    void delete(Integer id);
}
