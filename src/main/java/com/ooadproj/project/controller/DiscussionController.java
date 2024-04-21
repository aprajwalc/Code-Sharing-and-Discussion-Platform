package com.ooadproj.project.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ooadproj.project.entity.Discussions;
import com.ooadproj.project.entity.File;
import com.ooadproj.project.entity.FileContent;
import com.ooadproj.project.entity.GroupMembers;
import com.ooadproj.project.entity.Groups; 
import com.ooadproj.project.service.DiscussionService;
import com.ooadproj.project.service.FileContentService;
import com.ooadproj.project.service.FileService;
import com.ooadproj.project.service.GroupMembersService;
import com.ooadproj.project.service.GroupService;
import com.ooadproj.project.service.UserService;

class UserHolder {
    public static HashMap <Long, HashMap<Long, String>> groupusers = new HashMap<>();
}

class GroupWrapper {
    private Groups group;
    private Integer notification;

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Integer getNotification() {
        return notification;
    }

    public void setNotification(Integer notification) {
        this.notification = notification;
    }
}

@Controller
public class DiscussionController {
    private final UserService uService;
    private final DiscussionService dService;
    private final GroupService gService;
    private final GroupMembersService gmService;
    private final FileService fService;
    private final FileContentService fcService;

    DiscussionController(UserService uService, DiscussionService dService, GroupService gService, GroupMembersService gmService, FileService fService, FileContentService fcService){
        this.uService = uService;
        this.dService = dService;
        this.gService = gService;
        this.gmService = gmService;
        this.fService = fService;
        this.fcService = fcService;
    }

    @GetMapping("/discussion")
    public ModelAndView discussion(Model model) {
        model.addAttribute("userid", InnerMainController.userid);
        model.addAttribute("gid", InnerMainController.gid);
        model.addAttribute("groupname", InnerMainController.groupname);

        List<Long> gids = this.gmService.allGroups(InnerMainController.userid);
        if(gids.isEmpty()){
            model.addAttribute("groups", "empty");
            model.addAttribute("messages", "empty");
            model.addAttribute("gids", "empty");
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

        List <File> files = this.fService.getAllFiles(InnerMainController.userid);
        if(files.isEmpty())
            model.addAttribute("files", "empty");
        else
            model.addAttribute("files", files);
        
        ModelAndView discussion = new ModelAndView();
        discussion.setViewName("discussion");
        return discussion;
    }

    @PostMapping("/api/checkUser")
    @ResponseBody
    public String sendCode(@RequestBody String username){
        Long val = this.uService.checkUser(username);
        System.out.println(val);
        if(val > 0 && val != InnerMainController.userid)
        return Long.toString(val);
        else
        return "Fail";
    }

    @PostMapping("/api/addMember")
    @ResponseBody
    public String addMember(@RequestBody GroupMembers member){
        member.setJoined_date();
        member.setLastseen();
        this.gmService.saveMember(member);
        UserHolder.groupusers.get(member.getGid()).put(member.getUserid(), this.uService.getUsername(member.getUserid()));
        return "Success";
    }

    @PostMapping("/api/getDiscussions")
    @ResponseBody
    public List<Discussions> getDiscussions(@RequestBody Long gid){
        return this.dService.getAll(gid);
    }

    @PostMapping("/api/getLastMessage")
    @ResponseBody
    public Discussions getLastMessage(@RequestBody Long gid){
        return this.dService.getLast(gid);
    }

    @PostMapping("/api/sendUserMappings")
    @ResponseBody
    public HashMap<Long, String> sendUsers(@RequestBody Long gid){
        return UserHolder.groupusers.get(gid);
    }

    @PostMapping("/api/storeMessage")
    @ResponseBody
    public String storeMessage(@RequestBody Discussions message){
        message.setUserid(InnerMainController.userid);
        message.setTime();
        this.dService.storeMessage(message);
        return "Success";
    }

    @PostMapping("/api/sendEditorCode")
    @ResponseBody
    public String sendEditorCode(@RequestBody CodeWrapper code){
        File file = code.getFile();
        file.setCreated_at();
        FileContent content = code.getContent();
        file.setUserid(InnerMainController.userid);
        this.fService.storeCode(file);
        content.setID(file.getId());
        this.fcService.storeCode(content);
        InnerMainController.cfolderpath = code.getFile().getParentfolder();
        InnerMainController.fid = file.getId();
        return "Success";
    }

    @PostMapping("/api/interEditorReadonly")
    @ResponseBody
    public String interEditor(@RequestBody Long fid){
        if(this.fService.checkFile(fid)){
            InnerMainController.fid = fid;
            return "Success";
        }
        else{
            return "Notpresent";
        }
    }

    @GetMapping("/editoreadonly")
    public ModelAndView readonly(Model model){
        FileContent file = this.fcService.getCode(InnerMainController.fid);
        String name = this.fService.getName(InnerMainController.fid);
        List<String> folders = fService.getFolders(InnerMainController.userid);

        model.addAttribute("folders", folders);
        model.addAttribute("name", name);
        model.addAttribute("cfid", InnerMainController.fid);
        model.addAttribute("code", file.getCode());

        ModelAndView editor = new ModelAndView();
        editor.setViewName("editoreadonly");
        return editor;
    }

    @PostMapping("/api/storeLastSeen")
    @ResponseBody
    public String storeLastSeen(@RequestBody GroupMembers gm){
        this.gmService.savelastseen(gm.getGid(), gm.getUserid());
        return "Success";
    }

    @PostMapping("/api/getGroupInfo")
    @ResponseBody
    public List<String> getGroupInfo(@RequestBody Long gid){
        Groups g = this.gService.oneGroup(gid);
        System.out.println(gid);
        List<Long> gm = this.gmService.allMembers(gid);
        List<String> info = new ArrayList<>();
        info.add(g.getGdesc());
        for (Long id : gm) {
            if(id == g.getAdminid()){
                info.add("Admin: \n"+this.uService.getUsername(id)+"\n");
                info.add("Members: \n");
            }
            else
            info.add(this.uService.getUsername(id));
        }
        return info;
    }
}