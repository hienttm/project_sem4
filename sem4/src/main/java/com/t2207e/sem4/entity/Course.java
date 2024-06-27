package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseId")
    private int courseId;

    @Column(name = "courseName")
    @NotNull(message = "CourseName cannot be null")
    private String courseName;

    @Column(name = "courseVideo")
    @NotNull(message = "CourseVideo cannot be null")
    private String courseVideo = "/home/images/NoVideo.mp4";

    @Column(name = "courseFile")
    @NotNull(message = "CourseFile cannot be null")
    private String courseFile;

    @Column(name = "price")
    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must > 0")
    private double price;

    private double salePrice=0;

    @Column(name = "freeNumbers")
    @NotNull(message = "FreeNumbers cannot be null")
    private int freeNumbers;

    @Column(name = "image")
    @NotNull(message = "Image cannot be null")
    private String image = "/home/images/NoImage.png";

    @Column(name = "status")
    @NotNull(message = "FreeNumbers cannot be null")
    private int status=5;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "courseType_id", nullable = false)
    private CourseType courseType;

    @ManyToOne
    @JoinColumn(name = "censor_id", nullable = false)
    private User censor;

    private String description;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;
}
