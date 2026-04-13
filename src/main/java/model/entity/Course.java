package model.entity;

import jakarta.persistence.*;
import util.LocaleManager;

import java.util.Locale;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseId", nullable = false)
    private Integer id;

    @Column(name = "nameEn", nullable = false, length = 50)
    private String nameEn;

    @Column(name = "nameFi", nullable = false, length = 50)
    private String nameFi;

    @Column(name = "nameFa", nullable = false, length = 50)
    private String nameFa;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        return switch (lang) {
            case "en" -> nameEn;
            case "fi" -> nameFi;
            case "fa" -> nameFa;
            default -> nameEn;
        };
    }

    public void setName(String name) {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        switch (lang) {
            case "en" -> this.nameEn = name;
            case "fi" -> this.nameFi = name;
            case "fa" -> this.nameFa = name;
        }
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}