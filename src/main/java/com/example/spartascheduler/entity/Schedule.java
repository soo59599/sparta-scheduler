package com.example.spartascheduler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Schedule extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    private String title;
    private String content;

    public Schedule(String name, String password, String title, String content) {
        this.name = name;
        this.password = password;
        this.title = title;
        this.content = content;
    }

    public void updateTitleAndName(String title, String name){
        if(title!=null){
            this.title = title;
        }
        if(name!=null){
            this.name = name;
        }
    }






}
