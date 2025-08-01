package com.example.spartascheduler.dto.schedule;

import com.example.spartascheduler.dto.comment.CommentResponseDto;
import com.example.spartascheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleWithCommentsResponseDto {

    private String name;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto>  comments;

    public ScheduleWithCommentsResponseDto(Schedule schedule, List<CommentResponseDto> comments) {
        this.name = schedule.getName();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.comments = comments;
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
