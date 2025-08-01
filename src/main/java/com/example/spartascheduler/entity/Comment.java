package com.example.spartascheduler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;

    private String name;

    private String password;

    private String content;

    public Comment(Long scheduleId, String name, String password, String content) {
        this.scheduleId = scheduleId;
        this.name = name;
        this.password = password;
        this.content = content;
    }
}
