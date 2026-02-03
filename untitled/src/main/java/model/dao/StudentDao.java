package model.dao;

import model.entity.Student;
import java.util.List;

public interface StudentDao {

    void create(Student student);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByEnrollmentYear(Integer year);

    void update(Student student);

    void delete(Integer id);
}
