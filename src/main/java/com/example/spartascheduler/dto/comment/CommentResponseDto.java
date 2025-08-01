package com.example.spartascheduler.dto.comment;

import com.example.spartascheduler.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private String name;

    private String content;

    public CommentResponseDto(Comment comment) {
        this.name = comment.getName();
        this.content = comment.getContent();
    }
}
