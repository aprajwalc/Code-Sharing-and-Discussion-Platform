package com.ooadproj.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.FileContent;

@Repository
public interface FileContentRepository extends JpaRepository<FileContent , Long> {
    FileContent findByID(Long id);
}