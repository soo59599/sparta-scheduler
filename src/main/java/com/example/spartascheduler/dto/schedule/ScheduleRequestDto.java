package com.example.spartascheduler.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {

    private String name;
    private String password;

    private String title;
    private String content;
}
