package com.example.spartascheduler.service;

import com.example.spartascheduler.dto.ScheduleRequestDto;
import com.example.spartascheduler.dto.ScheduleResponseDto;
import com.example.spartascheduler.entity.Schedule;
import com.example.spartascheduler.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getTitle(), dto.getContents());
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules(String name) {
        List<Schedule> schedules = new ArrayList<>();
        if (name == null) {
            schedules.addAll(scheduleRepository.findAll());
        } else {
            scheduleRepository.findAll().stream().filter(schedule -> schedule.getName().equals(name)).forEach(schedules::add);
        }

        return schedules.stream().map(ScheduleResponseDto::new).toList();

    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 일정이 존재하지 않습니다.")));
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
