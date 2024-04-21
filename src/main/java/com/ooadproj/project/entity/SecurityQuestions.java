package com.ooadproj.project.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "securityquestions")
public class SecurityQuestions {
    @Id
    private Long userid;
    private String q1;
    private String q2;
    private String a1;
    private String a2;

    public SecurityQuestions(Long userid, String q1, String q2, String a1, String a2){
        this.userid = userid;
        this.q1 = q1;
        this.q2 = q2;
        this.a1 = a1;
        this.a2 = a2;
    }

    public SecurityQuestions(){
        
    }

    public void setUserID(Long userid){
        this.userid = userid;
    }

    public void setQ1(String q1){
        this.q1 = q1;
    }

    public void setQ2(String q2){
        this.q2 = q2;
    }

    public void setA1(String a1){
        this.a1 = a1;
    }

    public void setA2(String a2){
        this.a2 = a2;
    }

    public List<String> getQuestions(){
        List<String> strings = new ArrayList<>();
        strings.add(this.q1);
        strings.add(this.q2);
        return strings;
    }

    public List<String> getAnswer(){
        List<String> strings = new ArrayList<>();
        strings.add(this.a1);
        strings.add(this.a2);
        return strings;
    }
}
