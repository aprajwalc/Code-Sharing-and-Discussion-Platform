package com.ooadproj.project.controller;

import java.util.Stack;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ooadproj.project.entity.File;
import com.ooadproj.project.entity.SecurityQuestions;
import com.ooadproj.project.entity.Users;
import com.ooadproj.project.service.FileService;
import com.ooadproj.project.service.SecurityQuestionsService;
import com.ooadproj.project.service.UserService;

class InnerMainController {
    static Long userid = Long.parseLong("-1");
    static String username = null;
    static String cfolderpath = null;
    static Stack<String> folderpaths = new Stack<>();
    static Long fid = Long.parseLong("0");
    static Long gid = Long.parseLong("0");
    static String groupname = null;
}

@RestController
public class MainController{
    private final UserService uService;
    private final SecurityQuestionsService qService;
    private final FileService fService;    

    public MainController(UserService uService, SecurityQuestionsService qService, FileService fService) {
        this.uService = uService;
        this.qService = qService;
        this.fService = fService;
    }

    @GetMapping("/")
    public ModelAndView front () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView; 
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView; 
    }

    @GetMapping("/security")
    public ModelAndView security() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("securityquestions");
        return modelAndView;
    }

    @GetMapping("/hello")
    public ModelAndView hello(Model model) {
        String message = "Hello from Spring boot";
        model.addAttribute("message", message);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @PostMapping("/api/sendUserInfo")
    @ResponseBody
    public String sendUserInfo(@RequestBody Users userdata) {
        userdata.setReg_date();
        this.uService.storedata(userdata);
        InnerMainController.userid = userdata.getID();
        File file = new File();
        file.setName("S:");
        file.setType("folder");
        file.setUserid(userdata.getID());
        this.fService.storeCode(file);
        return "Success";
    }

    @PostMapping("/api/storeSecurityInfo")
    @ResponseBody
    public String sendSecurityInfo(@RequestBody SecurityQuestions values) {
        values.setUserID(InnerMainController.userid);
        this.qService.storeInfo(values);
        return "Success";
    }

    @PostMapping("/api/authenticateInfo")
    @ResponseBody
    public String authUserInfo(@RequestBody Users userdata) {
        boolean value = this.uService.authenticate(userdata.getUsername(), userdata.getPassword());
        if(value){
            // uService.setUsername(userdata.getUsername());
            InnerMainController.userid = userdata.getID();
            InnerMainController.username = userdata.getUsername();
            InnerMainController.cfolderpath = "S:";
            return "Success";
        }
        else
        return "Fail";
    }
}