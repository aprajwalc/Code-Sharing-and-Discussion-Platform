package com.ooadproj.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.Groups;
import com.ooadproj.project.repository.GroupsRepository;

@Service
public class GroupService {
    @Autowired
    private GroupsRepository repo;

    // @SuppressWarnings("null")
    public void storeGroup(Groups group){
        repo.save(group);
    }

    public List<Groups> findGroups(Long adminid){
        return repo.findAllByadminid(adminid);
    }

    public Timestamp getTime(Long gid){
        return repo.findByGid(gid).getCreated_date();
    }

    public List<Groups>findAllGroups(){
        return repo.findAll();
    }

    public Groups oneGroup(Long gid){
        return this.repo.findByGid(gid);
    }

    public List<Groups> allGroups(List<Long> gids){
        List<Groups> groups = new ArrayList<>();
        for (Long gid : gids) {
            groups.add(this.repo.findByGid(gid));
        }
        return groups;
    }
}