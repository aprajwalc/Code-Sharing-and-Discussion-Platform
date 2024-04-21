package com.ooadproj.project.entity;
import java.sql.*;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;
    private String name;
    private Long adminid;
    private Timestamp created_date;
    private String gdesc;
    private int maxlimit = 100;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAdminid() {
        return adminid;
    }

    public void setAdminid(Long admin_id) {
        this.adminid = admin_id;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.created_date = Timestamp.valueOf(currentTime);
    }

    public String getGdesc() {
        return gdesc;
    }

    public void setGdesc(String gdesc) {
        this.gdesc = gdesc;
    }

    public int getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(int maxlimit) {
        this.maxlimit = maxlimit;
    }
}