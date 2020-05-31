package com.example.k8s.springbootk8smysql.services;

import com.example.k8s.springbootk8smysql.entity.SynthXact;
import com.example.k8s.springbootk8smysql.repository.SynthXactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class SynthXactServiceImpl implements SynthXactService{
    @Autowired
    private SynthXactRepository synRepo;
    private List<String> openStatusList = Arrays.asList("RUNNING","INITIATE", "LATE");
    private List<String> finalStatusList = Arrays.asList("COMPLETE","ABANDON");
    private List<String> orphanedStatusList = Arrays.asList("ORPHAN", "UNORPHAN");

    private HashMap<String, HashMap<String, String>> allowedRoutes = new HashMap<String, HashMap<String, String>>();

    @Override
    @PostConstruct
    public void startUp() {
        List<String> allStatuses = new ArrayList<>();
        Stream.of(openStatusList).forEach(allStatuses::addAll);
        Stream.of(finalStatusList).forEach(allStatuses::addAll);
        Stream.of(orphanedStatusList).forEach(allStatuses::addAll);
        for (String i: allStatuses){
            HashMap<String, String> innerHM = new HashMap<String, String>();
            allowedRoutes.put(i, innerHM);
            for (String j: allStatuses){
                innerHM.put(j,"Not Allowed");
            }

            if (i.equals("INITIATE")) {
                allowedRoutes.get(i).put("RUNNING", "Allowed");
            }
            if (i.equals("RUNNING")) {
                allowedRoutes.get(i).put("RUNNING", "Allowed");
                allowedRoutes.get(i).put("LATE", "Allowed");
                allowedRoutes.get(i).put("COMPLETE", "Allowed");
            }
            if (i.equals("LATE")) {
                allowedRoutes.get(i).put("LATE", "Allowed");
                allowedRoutes.get(i).put("ORPHAN", "Allowed");
                allowedRoutes.get(i).put("COMPLETE", "Allowed");
            }
            if (i.equals("ORPHAN")) {
                allowedRoutes.get(i).put("ORPHAN", "Allowed");
                allowedRoutes.get(i).put("UNORPHAN", "Allowed");
                allowedRoutes.get(i).put("ABANDON", "Allowed");
            }

            if (i.equals("UNORPHAN")) {
                allowedRoutes.get(i).put("LATE", "Allowed");
                allowedRoutes.get(i).put("COMPLETE", "Allowed");
            }
        }
        //log.info("My new allowed List {}", allStatuses);
    }


    @Override
    public Optional<SynthXact> saveSynthXact(SynthXact mySynth) {
        synRepo.save(mySynth);
        return Optional.of(mySynth);
    }

    @Override
    public HashMap<String, HashMap<String, String>> getAllowedRoutes() {
        return allowedRoutes;
    }

    @Override
    public List<SynthXact> getAllSynthXact() {
        return synRepo.findAll();
    };

    @Override
    public List<SynthXact> getAllOpenSynthXact() {
        return synRepo.findByStatusIn(openStatusList);
    }

    @Override
    public List<SynthXact> getAllClosedSynthXact() {
        return synRepo.findByStatusIn(finalStatusList);
    }

    @Override
    public Long countByStatus(String status) {
        return synRepo.countByStatus(status);
    }

    @Override
    public Long countByStatusIn(List<String> statusesToCount) {
        return synRepo.countByStatusIn(statusesToCount);
    }

    @Override
    public Optional<SynthXact> getSynthXactByID(UUID id) {
        Optional<SynthXact> mySynth = synRepo.findById(id);
        return mySynth;
    }

    // **********************************************************************
    // Testing Functions
    // **********************************************************************
    @Override
    public void setAllInitiateForStatus(List<String> statusesToChange) {
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.start();
            synRepo.save(i);
        }
    }
    @Override
    public void setAllCompleteForStatus(List<String> statusesToChange) {
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.endComplete();
            synRepo.save(i);
        }
    }

    @Override
    public void setAllAbandonForStatus(List<String> statusesToChange) {
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.endAbandon();
            synRepo.save(i);
        };
    }

    @Override
    public void setAllOrphanForStatus(List<String> statusesToChange) {
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.updateOrphan();
            synRepo.save(i);
        }
    }
    public void setAllRunningForStatus(List<String> statusesToChange){
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.updateOrphan();
            synRepo.save(i);
        }
    }

    public void setAllUnOrphanForStatus(List<String> statusesToChange){
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.updateUnOrphan();
            synRepo.save(i);
        }
    }

    public void setAllLateForStatus(List<String> statusesToChange){
        for (SynthXact i : synRepo.findByStatusIn(statusesToChange)){
            i.updateLate();
            synRepo.save(i);
        }
    }

    // CONVENIENCE
    @Override
    public void setAllUnOrphanForOrphans() {
        for (SynthXact i : synRepo.findByStatusIn(Arrays.asList("ORPHAN"))){
            i.updateUnOrphan();
            synRepo.save(i);
        };
    }
    @Override
    public void setAllInitiatedToRunning() {
        for (SynthXact i : synRepo.findByStatusIn(Arrays.asList("INITIATE"))){
            i.updateRunning();
            synRepo.save(i);
        };
    }



// **********************************************************************
// Not Completed
// **********************************************************************

    @Override
    public SynthXact generateNewSynthXact(SynthXact scaledDown) {
        return null;
    }

    @Override
    public void setAutoRunning() {

    }

    @Override
    public void setAutoAbandoned() {

    }

    @Override
    public void setAutoLate() {

    }

    @Override
    public void setAutoOrphaned() {

    }

    @Override
    public Boolean validateStateFlowRequest(String currState, String requestedState) {
        return null;
    }

    @Override
    public Boolean getAllowedStateFlows(String currState) {
        return null;
    }

    @Override
    public Boolean getCurrentStatusByID(UUID id) {
        return null;
    }

    @Override
    public Boolean setInitiatedByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setRunningByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setCompletedByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setLateByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setOrphanedByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setAbandonedByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

    @Override
    public Boolean setUnOrphanedByID(Boolean overrideStateFlow, UUID id) {
        return null;
    }

}
