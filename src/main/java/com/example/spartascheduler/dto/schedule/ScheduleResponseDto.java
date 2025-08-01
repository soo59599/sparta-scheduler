package com.example.spartascheduler.dto.schedule;

import com.example.spartascheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private String name;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.name = schedule.getName();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
