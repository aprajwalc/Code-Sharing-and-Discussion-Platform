package com.ooadproj.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.File;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File , Long> {
    List<File> findByType(String type);
    List<File> findByParentfolder(String parent_folder);
    List<File> findByUserid(Long userid);
    List<File> findByName(String name);
    List<File> findByUseridAndType(Long userid, String type);
    List<File> findByUseridAndParentfolder(Long userid, String parent_folder);
    @SuppressWarnings("null")
    void deleteById(Long id);
}