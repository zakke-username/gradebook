package model.dao;

import model.entity.Course;
import java.util.List;

public interface CourseDao {

    void create(Course course);

    Course findById(Integer id);

    List<Course> findAll();

    List<Course> findByTeacherId(Integer teacherId);

    void update(Course course);

    void delete(Integer id);
}

