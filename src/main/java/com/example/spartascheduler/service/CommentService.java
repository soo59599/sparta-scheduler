package com.example.spartascheduler.service;

import com.example.spartascheduler.dto.comment.CommentRequestDto;
import com.example.spartascheduler.dto.comment.CommentResponseDto;
import com.example.spartascheduler.entity.Comment;
import com.example.spartascheduler.repository.CommentRepository;
import com.example.spartascheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    //댓글 생성
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto dto) {

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");
        }

        validateNameAndPassword(dto);
        validateComment(dto);

        long count = commentRepository.countByScheduleId(scheduleId);

        if (count >= 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "한 게시글당 댓글은 10개까지만 작성 가능합니다.");
        }

        Comment comment = new Comment(scheduleId, dto.getName(), dto.getPassword(), dto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    //댓글 확인
    private void validateComment(CommentRequestDto dto) {
        String content = dto.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용 입력은 필수값 입니다.");
        }
        if (content.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 100자 이내로 작성해야 합니다.");
        }
    }

    //이름과 비밀번호 확인
    private void validateNameAndPassword(CommentRequestDto dto) {
        String name = dto.getName();
        String password = dto.getPassword();

        if (name == null || name.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자 이름과 비밀번호는 필수값 입니다.");
        }
    }
}
