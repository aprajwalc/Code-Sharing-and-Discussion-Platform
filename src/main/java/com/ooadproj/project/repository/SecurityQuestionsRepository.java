package com.ooadproj.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.SecurityQuestions;

@Repository
public interface SecurityQuestionsRepository extends JpaRepository<SecurityQuestions, Long>{
    SecurityQuestions findByuserid(Long id);
}
