package com.ooadproj.project.entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GroupMembers")
public class GroupMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    private Long gid;
    private Timestamp joined_date;
    private Timestamp lastseen;

    public GroupMembers(Long id, Long userid, Long gid, Timestamp joined_date, Timestamp lastseen) {
        this.id = id;
        this.userid = userid;
        this.gid = gid;
        LocalDateTime currentTime = LocalDateTime.now();
        this.joined_date = Timestamp.valueOf(currentTime);
        this.lastseen = Timestamp.valueOf(currentTime);
    }

    public GroupMembers() {
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

    public Timestamp getJoined_date() {
        return joined_date;
    }

    public void setJoined_date() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.joined_date = Timestamp.valueOf(currentTime);
    }

    public Timestamp getLastseen() {
        return lastseen;
    }

    public void setLastseen() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.lastseen = Timestamp.valueOf(currentTime);
    }
}
