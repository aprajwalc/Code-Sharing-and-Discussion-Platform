package com.ooadproj.project.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ooadproj.project.entity.Discussions;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussions, Long> {
    Discussions findByuserid(Long userid);
    List<Discussions> findAllById(Long id);
    List<Discussions> findFirst10ByGidOrderByTimeDesc(Long groupId);
    List<Discussions> findAllBygid(Long gid);
    List<Discussions> findAllByUseridAndGidAndTime(Long userid, Long gid, Timestamp time);
    @Query(value = "SELECT * FROM discussions d WHERE d.gid = ?1 ORDER BY d.time DESC LIMIT 1", nativeQuery = true)
    Discussions findTopByUserIdAndGidAndTimeOrderByTimeDesc(Long gid);
    @Query(value = "select * from discussions d where d.gid = ?1 and d.time >= ?2", nativeQuery = true)
    List<Discussions> findNewMessages(Long gid, Timestamp time);
}
