package model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import util.LocaleManager;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignmentId", nullable = false)
    private Integer id;

    @Column(name = "titleEn", nullable = false, length = 50)
    private String titleEn;

    @Column(name = "titleFi", nullable = false, length = 50)
    private String titleFi;

    @Column(name = "titleFa", nullable = false, length = 50)
    private String titleFa;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "maxScore", nullable = false)
    private Integer maxScore;

    @Column(name = "typeEn", nullable = false, length = 50)
    private String typeEn;

    @Column(name = "typeFi", nullable = false, length = 50)
    private String typeFi;

    @Column(name = "typeFa", nullable = false, length = 50)
    private String typeFa;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "courseId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getTitle() {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        return switch (lang) {
            case "en" -> titleEn;
            case "fi" -> titleFi;
            case "fa" -> titleFa;
            default -> titleEn;
        };
    }

    public void setTitle(String title) {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        switch(lang) {
            case "en" -> this.titleEn = title;
            case "fi" -> this.titleFi = title;
            case "fa" -> this.titleFa = title;
        }
    }


    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public String getType() {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        return switch (lang) {
            case "en" -> typeEn;
            case "fi" -> typeFi;
            case "fa" -> typeFa;
            default -> typeEn;
        };
    }

    public void setType(String type) {
        LocaleManager lm = LocaleManager.getInstance();
        String lang = lm.getLanguage();
        switch(lang) {
            case "en" -> this.typeEn = type;
            case "fi" -> this.typeFi = type;
            case "fa" -> this.typeFa = type;
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}