package com.example.spartascheduler.controller;

import com.example.spartascheduler.dto.comment.CommentRequestDto;
import com.example.spartascheduler.dto.comment.CommentResponseDto;
import com.example.spartascheduler.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto dto) {
        return new ResponseEntity<>(commentService.createComment(scheduleId, dto), HttpStatus.CREATED);
    }


}
