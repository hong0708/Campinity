package com.ssafy.campinity.demo.batch.repository;

import com.ssafy.campinity.demo.batch.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Integer> {
}
