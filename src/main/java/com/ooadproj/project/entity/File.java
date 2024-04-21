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
@Table(name = "File")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    private String name;
    private String parentfolder;
    private String type;
    private Timestamp created_at;

    public Long getId(){
        return this.id;
    }

    public Long getUserid() {
        return this.userid;
    }

    public String getName() {
        return this.name;
    }

    public String getParentfolder() {
        return this.parentfolder;
    }

    public String getType() {
        return this.type;
    }

    public Timestamp getCreated_at(){
        return this.created_at;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_folder(String parent_folder) {
        this.parentfolder = parent_folder;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreated_at(){
        LocalDateTime currentTime = LocalDateTime.now();
        this.created_at = Timestamp.valueOf(currentTime);
    }
}
