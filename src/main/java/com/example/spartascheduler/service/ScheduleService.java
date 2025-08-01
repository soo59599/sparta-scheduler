package com.example.spartascheduler.service;

import com.example.spartascheduler.dto.comment.CommentResponseDto;
import com.example.spartascheduler.dto.schedule.ScheduleRequestDto;
import com.example.spartascheduler.dto.schedule.ScheduleResponseDto;
import com.example.spartascheduler.dto.schedule.ScheduleWithCommentsResponseDto;
import com.example.spartascheduler.entity.Schedule;
import com.example.spartascheduler.repository.CommentRepository;
import com.example.spartascheduler.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository  commentRepository;


    //일정 생성
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {

        validateNameAndPassword(dto);
        validateTitle(dto);
        validateContent(dto);

        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getTitle(), dto.getContent());

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    //일정 전체 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules(String name) {
        return scheduleRepository.findAll().stream()
                //name null 판단
                .filter(schedule -> name == null || schedule.getName().equals(name))
                //내림차순
                .sorted(Comparator.comparing(Schedule::getModifiedAt).reversed())
                //dto로 변환
                .map(ScheduleResponseDto::new)
                .toList();
    }

    //일정 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {

        return new ScheduleResponseDto(getScheduleById(id));
    }

    //일정 단건 댓글과 조회
    @Transactional(readOnly = true)
    public ScheduleWithCommentsResponseDto findScheduleByIdWithComment(Long id) {

        Schedule schedule = getScheduleById(id);

        List<CommentResponseDto> commentList = new ArrayList<>();
        commentRepository.findByScheduleId(id).forEach(comment -> commentList.add(new CommentResponseDto(comment)));

        return new  ScheduleWithCommentsResponseDto(schedule, commentList);
    }

    //일정 업데이트
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (dto.getTitle() == null && dto.getName() == null) {
            throw new IllegalArgumentException("수정할 데이터가 없습니다.");
        }

        if(dto.getTitle()!=null){
            validateTitle(dto);
        }

        Schedule schedule = getScheduleById(id);

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀 번호가 일치하지 않습니다.");
        }

        schedule.updateTitleAndName(dto.getTitle(), dto.getName());

        return new ScheduleResponseDto(schedule);
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long id, ScheduleRequestDto dto) {

        Schedule schedule = getScheduleById(id);

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀 번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(id);
    }





    //이름과 비밀번호 확인
    private void validateNameAndPassword(ScheduleRequestDto dto) {
        String name = dto.getName();
        String password = dto.getPassword();

        if (name == null || name.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("이름과 비밀번호는 필수값 입니다.");
        }
    }

    //일정 찾기
    private Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다."));
    }

    //제목 확인
    private void validateTitle(ScheduleRequestDto dto) {
        String title = dto.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목 입력은 필수값 입니다.");
        }
        if(title.length()>30){
            throw new IllegalArgumentException("제목은 30자 이내로 작성해야 합니다.");
        }
    }

    //내용 확인
    private void validateContent(ScheduleRequestDto dto) {
        String content = dto.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("일정 입력은 필수값 입니다.");
        }
        if(content.length()>200){
            throw new IllegalArgumentException("일정은 200자 이내로 작성해야 합니다.");
        }
    }



}
