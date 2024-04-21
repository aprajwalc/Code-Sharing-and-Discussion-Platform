package com.ooadproj.project.entity;

import java.sql.*;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "discussions")
public class Discussions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gid;
    private Long userid;
    private Timestamp time;
    private String message;

    public Discussions(Long id, Long gid, Long userid, String message, Timestamp time){
        this.id = id;
        this.userid = userid;
        this.gid = gid;
        this.message = message;
        LocalDateTime currentTime = LocalDateTime.now();
        this.time = Timestamp.valueOf(currentTime);
    }

    public Discussions(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.time = Timestamp.valueOf(currentTime);
    }
}