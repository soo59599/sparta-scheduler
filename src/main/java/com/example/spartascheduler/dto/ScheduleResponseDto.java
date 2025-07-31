package com.example.spartascheduler.dto;

import com.example.spartascheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private String name;

    private String title;
    private String contents;

    public ScheduleResponseDto(Schedule schedule) {
        this.name = schedule.getName();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
    }

}
