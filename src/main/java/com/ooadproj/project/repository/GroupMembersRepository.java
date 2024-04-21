package com.ooadproj.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.GroupMembers;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long>{
    List<GroupMembers> findAllByGid(long gid);
    List<GroupMembers> findAllByUserid(Long userid);
    GroupMembers findByUseridAndGid(Long userid, Long gid);
}
