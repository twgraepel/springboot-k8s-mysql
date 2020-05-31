package com.example.k8s.springbootk8smysql.controller;

import com.example.k8s.springbootk8smysql.entity.SynthXact;
import com.example.k8s.springbootk8smysql.entity.User;
import com.example.k8s.springbootk8smysql.services.SynthXactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
public class SynthXactController {

    //@Autowired
    //private SynthXactRepository synthXactRepository;
    @Autowired
    private SynthXactService svc;

    @GetMapping("/synthetic/admin/allowed")
    HashMap<String, HashMap<String, String>> allowed() {
        return svc.getAllowedRoutes();
    }
    @GetMapping("/synthetics")
    public List<SynthXact> getSynthXacts() {
        return svc.getAllSynthXact();
    }

    @GetMapping("/synthetics/open")
    public List<SynthXact> getOpenSynthXact() {
        return svc.getAllOpenSynthXact();
    }

    @GetMapping("/synthetics/closed")
    public List<SynthXact> getClosedSynthXact() {
        return svc.getAllClosedSynthXact();
    }


    @GetMapping("/synthetic/get/{id}")
    public Optional<SynthXact> getSynthXact(@PathVariable UUID id) {
        return svc.getSynthXactByID(id);
    }

    @GetMapping("/synthetic/admin/{choice}")
    public String doAdminStuff(@PathVariable String choice) {
        switch (choice.toLowerCase()) {
            case "restart-all":
                svc.setAllInitiateForStatus(Arrays.asList("RUNNING", "LATE", "ORPHAN", "ABANDON", "COMPLETE", "UNORPHAN"));
                break;
            case "restart-open":
                svc.setAllInitiateForStatus(Arrays.asList("RUNNING", "LATE", "ORPHAN", "UNORPHAN"));
                break;
            case "initiate-to-running":
                svc.setAllRunningForStatus(Arrays.asList("INITIATE"));
                break;
            case "running-to-complete":
                svc.setAllCompleteForStatus(Arrays.asList("RUNNING"));
                break;
            case "unorphan-to-complete":
                svc.setAllCompleteForStatus(Arrays.asList("UNORPHAN"));
                break;
            case "late-to-complete":
                svc.setAllCompleteForStatus(Arrays.asList("LATE"));
                break;
            case "running-to-late":
                svc.setAllLateForStatus(Arrays.asList("RUNNING"));
                break;
            case "unorphan-to-late":
                svc.setAllLateForStatus(Arrays.asList("RUNNING"));
                break;
            case "orphan-to-abandon":
                svc.setAllAbandonForStatus(Arrays.asList("ORPHAN"));
                break;
            case "late-to-orphan":
                svc.setAllOrphanForStatus(Arrays.asList("LATE"));
                break;
            case "orphan-to-unorphan":
                svc.setAllUnOrphanForStatus(Arrays.asList("ORPHAN"));
                break;
            default:
                log.info("Bad Choice for Admin" + choice);
        }
        return "Admin Choice: " + choice;
    }
}

