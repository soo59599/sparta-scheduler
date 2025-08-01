package com.example.spartascheduler.repository;

import com.example.spartascheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    long countByScheduleId(Long scheduleId);

    List<Comment> findByScheduleId(Long scheduleId);

}
