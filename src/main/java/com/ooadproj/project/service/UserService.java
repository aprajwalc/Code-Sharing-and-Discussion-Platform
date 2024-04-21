package com.ooadproj.project.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ooadproj.project.entity.Users;
import com.ooadproj.project.repository.UserRepository;

@Service
public class UserService {
   @Autowired
   private UserRepository repo;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

    public UserService() {
        
    }
   
    public Users storedata(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return (Users)this.repo.save(user);
    }

    public boolean authenticate(String username, String password) {
        Users user = repo.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public Users getData(String username){
        Users user = repo.findByUsername(username);
        return user;
    }

    public Long checkUser(String username){
        Users user = repo.findByUsername(username);
        if(user != null){
            return user.getID();
        }
        else
        return Long.parseLong("0");
    }

    public String getUsername(Long userid){
        return this.repo.getReferenceById(userid).getUsername();
    }

    public Timestamp getJoinedDate(Long userid){
        return this.repo.getReferenceById(userid).getReg_date();
    }

    public HashMap<Long, String> groupUsers(List <Long> userids){
        HashMap<Long, String> users = new HashMap<>();
        for (Long id : userids) {
            users.put(id, this.repo.getReferenceById(id).getUsername());
        }
        return users;
    }
}