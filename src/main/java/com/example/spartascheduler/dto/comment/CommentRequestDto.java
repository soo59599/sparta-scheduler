package com.example.spartascheduler.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String name;

    private String password;

    private String content;
}
