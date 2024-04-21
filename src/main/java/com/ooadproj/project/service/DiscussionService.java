package com.ooadproj.project.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.Discussions;
import com.ooadproj.project.repository.DiscussionRepository;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionRepository repo;

    public void storeMessage(Discussions discuss){
        repo.save(discuss);
    }

    public List<Discussions> getAll(Long gid){
        return repo.findAllBygid(gid);
    }

    public Discussions getLast(Long gid){
        return repo.findAllBygid(gid).removeLast();
        // return repo.findLastMessageByGid(gid);
    }

    public HashMap<Long, Timestamp> sortByTime(HashMap <Long, Timestamp> created){
        HashMap <Long, Timestamp> hash = new HashMap<>();
        for (Map.Entry<Long, Timestamp> value : created.entrySet()) {
            Discussions d = this.repo.findTopByUserIdAndGidAndTimeOrderByTimeDesc(value.getKey());
            if(d == null){
                hash.put(value.getKey(), value.getValue());
            }
            else{
                hash.put(value.getKey(), d.getTime());
            }
        }
        HashMap<Long, Timestamp> result = hash.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<Long, Timestamp> value : result.entrySet()) {
            System.out.println(value.getKey().toString() + " Value: "+ value.getValue().toString());
        }
        return result;
    }

    public List<Discussions>getLatestDiscussionsByGroupId(Long groupId) {
        return repo.findFirst10ByGidOrderByTimeDesc(groupId);
    }

    public Integer getNewMessages(Long gid, Timestamp time){
        return this.repo.findNewMessages(gid, time).size();
    }
}