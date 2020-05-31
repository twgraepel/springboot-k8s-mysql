package com.example.k8s.springbootk8smysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.k8s.springbootk8smysql.entity.SynthXact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SynthXactRepository extends JpaRepository<SynthXact, UUID> {
    List<SynthXact> findByStatusNotIn(List<String> statuses);
    List<SynthXact> findByStatusIn(List<String> statuses);
    Long countByStatus(String status);
    Long countByStatusIn(List<String> statuses);
    SynthXact findFirstByStatusIn(List<String> statuses);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE SynthXact a SET a.status = :newStatus WHERE a.status in :statusesToChange")
//    void updateAllForNewStatus(List<String> statusesToChange, String newStatus);

}
