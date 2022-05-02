package com.example.dMaker.repository;

import com.example.dMaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
