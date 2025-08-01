package com.example.spartascheduler.service;

import com.example.spartascheduler.dto.comment.CommentRequestDto;
import com.example.spartascheduler.dto.comment.CommentResponseDto;
import com.example.spartascheduler.entity.Comment;
import com.example.spartascheduler.repository.CommentRepository;
import com.example.spartascheduler.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto dto) {
        boolean emptySchedule = scheduleRepository.findById(scheduleId).isEmpty();
        if (emptySchedule) {
            throw new EntityNotFoundException("해당 일정이 존재하지 않습니다.");
        }

        long count = commentRepository.findAll().stream().filter(comment -> comment.getScheduleId().equals(scheduleId)).count();

        if(count > 10){
            throw new IllegalArgumentException("한 게시글당 댓글은 10개까지 가능합니다.");
        }

        Comment comment = new Comment(scheduleId, dto.getName(), dto.getPassword(), dto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
