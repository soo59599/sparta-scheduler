package com.example.spartascheduler.controller;

import com.example.spartascheduler.dto.schedule.ScheduleRequestDto;
import com.example.spartascheduler.dto.schedule.ScheduleResponseDto;
import com.example.spartascheduler.dto.schedule.ScheduleWithCommentsResponseDto;
import com.example.spartascheduler.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    //일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(scheduleService.findAllSchedules(name), HttpStatus.OK);
    }

    //일정 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    //일정 단건 댓글과 조회
    @GetMapping("/{id}/with-comments")
    public ResponseEntity<ScheduleWithCommentsResponseDto> findScheduleByIdWithComment(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleByIdWithComment(id), HttpStatus.OK);
    }

    //일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
    }

    //일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {

        scheduleService.deleteSchedule(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
