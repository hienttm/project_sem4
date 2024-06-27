package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapterId")
    private int chapterId;

    @Column(name = "chapterName")
    @NotNull(message = "ChapterName cannot be null")
    private String chapterName;

    @Column(name = "chapterVideo")
    @NotNull(message = "ChapterVideo cannot be null")
    private String chapterVideo = "/home/images/NoVideo.mp4";

    @Column(name = "chapterFile")
    @NotNull(message = "ChapterFile cannot be null")
    private String chapterFile;

    @Column(name = "createAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "CreateAt cannot be null")
    private Date createAt = new Date(System.currentTimeMillis());

    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private int status=1;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Getters and Setters
}
