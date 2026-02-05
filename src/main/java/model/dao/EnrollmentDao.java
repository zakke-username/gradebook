package model.dao;

import model.entity.Enrollment;

import java.util.List;

public interface EnrollmentDao {

    void create(Enrollment enrollment);

    Enrollment findById(Integer id);

    List<Enrollment> findAll();

    List<Enrollment> findByStudentId(Integer studentId);

    List<Enrollment> findByCourseId(Integer courseId);

    void update(Enrollment enrollment);

    void delete(Integer id);
}
