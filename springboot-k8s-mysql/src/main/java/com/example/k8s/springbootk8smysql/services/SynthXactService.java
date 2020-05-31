package com.example.k8s.springbootk8smysql.services;

import com.example.k8s.springbootk8smysql.entity.SynthXact;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SynthXactService {
    Optional<SynthXact> getSynthXactByID(UUID id);
    Optional<SynthXact> saveSynthXact(SynthXact mySynth);
    SynthXact generateNewSynthXact(SynthXact scaledDown);
    List<SynthXact> getAllSynthXact();
    List<SynthXact> getAllOpenSynthXact();
    List<SynthXact> getAllClosedSynthXact();

    Long countByStatus(String status);
    Long countByStatusIn(List<String> statusesToCount);

    void startUp();

    void setAutoRunning();
    void setAutoAbandoned();
    void setAutoLate();
    void setAutoOrphaned();
    HashMap<String, HashMap<String, String>> getAllowedRoutes();

    Boolean validateStateFlowRequest(String currState, String requestedState);
    Boolean getAllowedStateFlows(String currState);

    Boolean getCurrentStatusByID(UUID id);
    Boolean setInitiatedByID(Boolean overrideStateFlow, UUID id);
    Boolean setRunningByID(Boolean overrideStateFlow, UUID id);
    Boolean setCompletedByID(Boolean overrideStateFlow, UUID id);
    Boolean setLateByID(Boolean overrideStateFlow, UUID id);
    Boolean setOrphanedByID(Boolean overrideStateFlow, UUID id);
    Boolean setAbandonedByID(Boolean overrideStateFlow, UUID id);
    Boolean setUnOrphanedByID(Boolean overrideStateFlow, UUID id);

    //void progressOneID(UUID id, String status);
    //void progressFirstID();
    void setAllInitiateForStatus(List<String> statusesToChange);
    void setAllCompleteForStatus(List<String> statusesToChange);
    void setAllAbandonForStatus(List<String> statusesToChange);
    void setAllOrphanForStatus(List<String> statusesToChange);
    void setAllRunningForStatus(List<String> statusesToChange);
    void setAllUnOrphanForStatus(List<String> statusesToChange);
    void setAllLateForStatus(List<String> statusesToChange);

    void setAllUnOrphanForOrphans();
    void setAllInitiatedToRunning();


}
