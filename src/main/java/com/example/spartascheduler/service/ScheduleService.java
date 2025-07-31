package com.example.spartascheduler.service;

import com.example.spartascheduler.dto.ScheduleRequestDto;
import com.example.spartascheduler.dto.ScheduleResponseDto;
import com.example.spartascheduler.entity.Schedule;
import com.example.spartascheduler.repository.ScheduleRepository;
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
        Schedule schedule = new Schedule(dto.getName(),dto.getPassword(),dto.getTitle(),dto.getContents());
        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedules() {
        List<Schedule> schedules = new ArrayList<>(scheduleRepository.findAll());
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {
        return new ScheduleResponseDto(scheduleRepository.findById(id).get());
    }
}
