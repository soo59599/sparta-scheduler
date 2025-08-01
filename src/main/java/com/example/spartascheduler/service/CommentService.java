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

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new EntityNotFoundException("해당 일정이 존재하지 않습니다.");
        }

        validateComment(dto);

        long count = commentRepository.countByScheduleId(scheduleId);

        if(count > 10){
            throw new IllegalArgumentException("한 게시글당 댓글은 10개까지 가능합니다.");
        }

        Comment comment = new Comment(scheduleId, dto.getName(), dto.getPassword(), dto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    //댓글 확인
    private void validateComment(CommentRequestDto dto) {
        String content = dto.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용 입력은 필수값 입니다.");
        }
        if(content.length()>100){
            throw new IllegalArgumentException("댓글 내용은 100자 이내로 작성해야 합니다.");
        }
    }
}
