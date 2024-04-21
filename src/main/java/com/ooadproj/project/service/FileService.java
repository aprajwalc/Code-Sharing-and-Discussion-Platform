package com.ooadproj.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooadproj.project.entity.File;
import com.ooadproj.project.repository.FileRepository;

@Service
public class FileService {
    @Autowired
    private FileRepository repo;

    // @SuppressWarnings("null")
    public void storeCode(File file){
        this.repo.save(file);
    }

    public List<File> getData(Long userid){
        List<File> files = repo.findByUserid(userid);
        return files;
    }

    public boolean checkFile(Long fid){
        Optional<File> file = this.repo.findById(fid);
        return file.isPresent();
    }

    public void deleteFile(Long fid){
        this.repo.deleteById(fid);
    }

    public String getName(Long fid){
        return this.repo.getReferenceById(fid).getName();
    }

    public List<File> getFiles(Long userid, String path){
        return this.repo.findByUseridAndParentfolder(userid, path);
    }

    public List<File> getAllFiles(Long userid){
        return this.repo.findByUseridAndType(userid, "file");
    }

    public List<String> getFolders(Long userid){
        List <File> folders = repo.findByUseridAndType(userid, "folder");
        List <String> path = new ArrayList<>();
        Map <String, String> fold = new HashMap<String, String>();
        folders.remove(0);
        for(int i=0; i<folders.size(); i++){
            if(folders.get(i).getParentfolder().equals("S:")){
                fold.put(folders.get(i).getName(), "S:"+folders.get(i).getName());
            }
            else{
                try {
                    fold.put(folders.get(i).getName(), fold.get(folders.get(i).getParentfolder())+"\\"+folders.get(i).getName());
                } catch (Exception e) {
                    System.out.println("Error occurred while passing\n");
                }
            }
        }
        path.add("S:");
        for (Map.Entry<String, String> filepath : fold.entrySet()) {
            path.add(filepath.getValue());
        }
        Collections.sort(path, Comparator.comparing(String::length));
        for (String s : path) {
            System.out.println(s);
        }
        return path;
    }
}
