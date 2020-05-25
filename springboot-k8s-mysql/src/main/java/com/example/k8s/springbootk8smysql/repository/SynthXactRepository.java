package com.example.k8s.springbootk8smysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.k8s.springbootk8smysql.entity.SynthXact;
import java.util.UUID;

public interface SynthXactRepository extends JpaRepository<SynthXact, UUID> {

}
