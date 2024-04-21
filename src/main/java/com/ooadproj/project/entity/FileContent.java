package com.ooadproj.project.entity;

import java.sql.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_content")
public class FileContent {
    @Id
    private Long id;
    private String code;
    private String language;
    private Timestamp lastmodified;

    public Long getID(){
        return this.id;
    }

    public void setID(Long id){
        this.id = id;
    }    

    public String getLanguage(){
        return this.language;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Timestamp getLastmodified(){
        return this.lastmodified;
    }

    public void setLastmodified(Timestamp lastmodified){
        this.lastmodified = lastmodified;
    }
}