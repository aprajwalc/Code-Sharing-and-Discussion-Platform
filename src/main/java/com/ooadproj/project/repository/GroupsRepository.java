package com.ooadproj.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.Groups;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {
    List<Groups> findAllByadminid(Long admin_id);
    Groups findByGid(Long gid);
}
