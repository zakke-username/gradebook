package model.dao;

import model.entity.Teacher;
import java.util.List;

public interface TeacherDao {

    void create(Teacher teacher);

    Teacher findById(Integer id);

    List<Teacher> findAll();

    List<Teacher> findByLastName(String lastName);

    void update(Teacher teacher);

    void delete(Integer id);
}
