package com.example.dMaker.repository;

import com.example.dMaker.code.StatusCode;
import com.example.dMaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByMemberId(String memberId);    //-Spring JPA에서는 메서드 명을 가지고도 특정 컬럼명을 검색해주는 기능이 있다

    List<Developer> findDevelopersByStatusCodeEquals(StatusCode statusCode);
}
