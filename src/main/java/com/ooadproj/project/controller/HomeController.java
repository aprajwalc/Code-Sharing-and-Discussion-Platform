package com.ooadproj.project.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.ooadproj.project.entity.File;
import com.ooadproj.project.entity.FileContent;
import com.ooadproj.project.entity.GroupMembers;
import com.ooadproj.project.entity.Groups;
import com.ooadproj.project.entity.Users;
import com.ooadproj.project.service.DiscussionService;
import com.ooadproj.project.service.FileContentService;
import com.ooadproj.project.service.FileService;
import com.ooadproj.project.service.GroupMembersService;
import com.ooadproj.project.service.GroupService;
import com.ooadproj.project.service.UserService;

class CodeWrapper {
    private File file;
    private FileContent content;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileContent getContent() {
        return content;
    }

    public void setContent(FileContent content) {
        this.content = content;
    }
}

@Controller
public class HomeController{
    private final UserService uService;
    private final GroupService gService;
    private final FileService fService;
    private final FileContentService fcService;
    private final GroupMembersService gmService;
    private final DiscussionService dService;
    
    HomeController(UserService uService, GroupService gService, GroupMembersService gmService,FileService fService, FileContentService fcService, DiscussionService dService){
        this.uService = uService;
        this.gService = gService;
        this.fService = fService;
        this.fcService = fcService;
        this.gmService = gmService;
        this.dService = dService;
    }

    @GetMapping("/home")
    public ModelAndView home(Model model) {
        Users user = uService.getData(InnerMainController.username);
        InnerMainController.userid = user.getID();
        List<File> files = fService.getData(user.getID());
        List<String> folders = fService.getFolders(user.getID());
        model.addAttribute("forf", files);
        if(files.size() == 1){
            model.addAttribute("val", "empty");
        }
        else{
            model.addAttribute("val", "non-empty");
        }
        model.addAttribute("userid", user.getID());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("folders", folders);
        model.addAttribute("folderpath", InnerMainController.cfolderpath);

        List<Long> gids = this.gmService.allGroups(InnerMainController.userid);
        if(gids.isEmpty()){
            model.addAttribute("groups", "empty");
        }
        else{
            HashMap <Long, String> users = new HashMap<>();
            HashMap <Long, Timestamp> dates = new HashMap<>();
            for (Long gid : gids) {
                List <Long> userids = this.gmService.allMembers(gid);
                for (Long userid : userids) {
                    users.put(userid, this.uService.getUsername(userid));
                }
                dates.put(gid, this.gService.getTime(gid));
                UserHolder.groupusers.put(gid, users);
            }
            HashMap<Long, Timestamp> sorted = this.dService.sortByTime(dates);
            gids = new ArrayList<>();
            List<GroupWrapper> groups = new ArrayList<>();
            for (Map.Entry<Long, Timestamp> value : sorted.entrySet()) {
                gids.add(value.getKey());
                GroupWrapper group = new GroupWrapper();
                group.setGroup(this.gService.oneGroup(value.getKey()));
                System.out.println(value.getKey().toString()+" Value: "+this.gmService.getlastseen(value.getKey(), InnerMainController.userid));
                group.setNotification(this.dService.getNewMessages(value.getKey(), this.gmService.getlastseen(value.getKey(), InnerMainController.userid)));
                groups.add(group);
            }
            model.addAttribute("groups", groups);
            model.addAttribute("gids", gids);
        }

        ModelAndView home = new ModelAndView();
        home.setViewName("home");
        return home;
    }

    @PostMapping("/api/sendCode")
    @ResponseBody
    public String sendCode(@RequestBody CodeWrapper code){
        // System.out.println(code);
        File file = code.getFile();
        FileContent content = code.getContent();
        file.setUserid(InnerMainController.userid);
        this.fService.storeCode(file);
        content.setID(file.getId());
        this.fcService.storeCode(content);
        return "Success";
    }

    @PostMapping("/api/sendFolder")
    @ResponseBody
    public String sendFolder(@RequestBody File file){
        // System.out.println(code);
        file.setUserid(InnerMainController.userid);
        this.fService.storeCode(file);
        return "Success";
    }

    @PostMapping("/api/groupInfo")
    @ResponseBody
    public String sendGroup(@RequestBody Groups group){
        group.setAdminid(InnerMainController.userid);
        group.setCreated_date();
        this.gService.storeGroup(group);
        GroupMembers gm = new GroupMembers();
        gm.setGid(group.getGid());
        gm.setUserid(InnerMainController.userid);
        gm.setJoined_date();
        gm.setLastseen();
        this.gmService.saveMember(gm);
        return "Success";
    }

    @PostMapping("/api/enterFolder")
    @ResponseBody
    public String enterFolder(@RequestBody String name){
        System.out.println(name);
        if(name.equals("previousfolder!!!!")){
            InnerMainController.cfolderpath = InnerMainController.folderpaths.pop();
        }
        else{
            InnerMainController.folderpaths.push(InnerMainController.cfolderpath);
            InnerMainController.cfolderpath = name;
        }
        return "Success";
    }

    @GetMapping("/editor")
    public ModelAndView editor(Model model) {
        FileContent file = this.fcService.getCode(InnerMainController.fid);
        String name = this.fService.getName(InnerMainController.fid);
        List<File> files = fService.getData(InnerMainController.userid);
        List<File> files1 = fService.getFiles(InnerMainController.userid, InnerMainController.cfolderpath);
        model.addAttribute("folderpath", InnerMainController.cfolderpath);
        model.addAttribute("files", files1);
        model.addAttribute("copy", files);
        model.addAttribute("name", name);
        model.addAttribute("cfid", InnerMainController.fid);
        model.addAttribute("code", file.getCode());

        ModelAndView editor = new ModelAndView();
        editor.setViewName("editor");
        return editor;
    }

    @PostMapping("/api/enterFile")
    @ResponseBody
    public String enterFile(@RequestBody String idstring) {
        Long id = Long.parseLong(idstring);
        if(this.fService.checkFile(id)){
            InnerMainController.fid = id;
            return "Success";
        }
        else
        return "Notpresent";
    }

    @PostMapping("/api/saveCode")
    @ResponseBody
    public String saveCode(@RequestBody FileContent codefile){
        this.fcService.storeCode(codefile);
        return "Success";
    }

    @GetMapping("/logout")
    public String logout(){
        InnerMainController.userid = Long.parseLong("-1");
        InnerMainController.username = null;
        InnerMainController.cfolderpath = null;
        InnerMainController.folderpaths = new Stack<>();
        InnerMainController.fid = Long.parseLong("0");
        return "redirect:login";
    }

    @PostMapping("/api/setGroup")
    @ResponseBody
    public String setGroup(@RequestBody Groups g){
        InnerMainController.gid = g.getGid();
        InnerMainController.groupname = g.getName();
        return "Success";
    }

    @GetMapping("/profile")
    public ModelAndView profile(Model model){
        model.addAttribute("username", InnerMainController.username);
        Timestamp date = this.uService.getJoinedDate(InnerMainController.userid);
        date.toLocalDateTime();
        String dateformat = "yyyy-MM-dd";
        SimpleDateFormat d = new SimpleDateFormat(dateformat);
        model.addAttribute("date", d.format(date));
        ModelAndView profile = new ModelAndView();
        profile.setViewName("profile");
        return profile;
    }

    @PostMapping("/api/deleteFile")
    @ResponseBody
    public String deleteFile(@RequestBody Long fid){
        this.fService.deleteFile(fid);
        return "Success";
    }
}
