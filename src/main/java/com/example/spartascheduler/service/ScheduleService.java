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


    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getTitle(), dto.getContent());

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

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

    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다.")));
    }

    @Transactional(readOnly = true)
    public ScheduleWithCommentsResponseDto findScheduleByIdWithComment(Long id) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다."));

        List<CommentResponseDto> commentList = new ArrayList<>();
        commentRepository.findByScheduleId(id).forEach(comment -> commentList.add(new CommentResponseDto(comment)));

        return new  ScheduleWithCommentsResponseDto(schedule, commentList);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (dto.getTitle() == null && dto.getName() == null) {
            throw new IllegalArgumentException("수정할 데이터가 없습니다.");
        }

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀 번호가 일치하지 않습니다.");
        }

        schedule.updateTitleAndName(dto.getTitle(), dto.getName());

        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id, ScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀 번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(id);
    }


}
