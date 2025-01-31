package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByClassEntityIdOrderBySequence(Long classId);
}