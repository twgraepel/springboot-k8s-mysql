package com.example.k8s.springbootk8smysql.repository;

import com.example.k8s.springbootk8smysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.k8s.springbootk8smysql.entity.SynthXact;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SynthXactRepository extends JpaRepository<SynthXact, UUID> {
    List<SynthXact> findByStatusNotIn(List<String> statuses);
}
