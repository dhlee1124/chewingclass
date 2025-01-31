package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    // 추가적인 쿼리 메서드가 필요하면 정의
}