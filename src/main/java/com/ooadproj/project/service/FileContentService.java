package com.ooadproj.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.FileContent;
import com.ooadproj.project.repository.FileContentRepository;

@Service
public class FileContentService {
    @Autowired
    private FileContentRepository repo;

    // @SuppressWarnings("null")
    public void storeCode(FileContent content){
        this.repo.save(content);
    }

    public FileContent getCode(Long id){
        return this.repo.getReferenceById(id);
    }
}