package com.ooadproj.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.GroupMembers;
import com.ooadproj.project.repository.GroupMembersRepository;

@Service
public class GroupMembersService {
    @Autowired
    private GroupMembersRepository repo;

    public void saveMember(GroupMembers member){
        this.repo.save(member);
    }

    public List<GroupMembers> displayGroups(long gid){
        return repo.findAllByGid(gid);
    }

    public void savelastseen(Long gid, Long userid){
        GroupMembers gm = this.repo.findByUseridAndGid(userid, gid);
        gm.setLastseen();
        this.repo.save(gm);
    }

    public Timestamp getlastseen(Long gid, Long userid){
        GroupMembers gm = this.repo.findByUseridAndGid(userid, gid);
        return gm.getLastseen();
    }

    public List<Long> allGroups(Long userid){
        List<Long> gids = new ArrayList<>();
        List<GroupMembers> gm = repo.findAllByUserid(userid);
        for (GroupMembers m : gm) {
            gids.add(m.getGid());
        }
        return gids;
    }

    public List <Long> allMembers(Long gid){
        List <GroupMembers> users = repo.findAllByGid(gid);
        List <Long> userids = new ArrayList<>();
        for (GroupMembers user : users) {
            userids.add(user.getUserid());
        }
        return userids;
    }

}