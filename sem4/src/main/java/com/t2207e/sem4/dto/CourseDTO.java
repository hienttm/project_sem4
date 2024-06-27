package com.t2207e.sem4.dto;

import com.t2207e.sem4.entity.Category;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    private int courseId;

    private String courseName;

    private String courseVideo;

    private String courseFile;

    private double price;

    private double salePrice;

    private int freeNumbers;

    private String image;

    private int status;

    private String username;

    private String categoryName;

    private String courseTypeName;

    private String censorName;
}
