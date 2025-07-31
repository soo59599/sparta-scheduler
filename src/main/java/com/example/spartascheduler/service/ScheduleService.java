package com.example.spartascheduler.service;

import com.example.spartascheduler.entity.Schedule;
import com.example.spartascheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Transactional
    public Schedule save(@RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

}
