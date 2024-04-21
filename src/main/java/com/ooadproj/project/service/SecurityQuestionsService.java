package com.ooadproj.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.SecurityQuestions;
import com.ooadproj.project.repository.SecurityQuestionsRepository;

@Service
public class SecurityQuestionsService {
    @Autowired
    private SecurityQuestionsRepository repo;

    // @SuppressWarnings("null")
    public void storeInfo(SecurityQuestions values){
        repo.save(values);
    }
    
}
