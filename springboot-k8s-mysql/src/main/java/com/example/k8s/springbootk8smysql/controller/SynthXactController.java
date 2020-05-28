package com.example.k8s.springbootk8smysql.controller;

import com.example.k8s.springbootk8smysql.entity.SynthXact;
import com.example.k8s.springbootk8smysql.entity.User;
import com.example.k8s.springbootk8smysql.repository.SynthXactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SynthXactController {

    @Autowired
    private SynthXactRepository synthXactRepository;

    @GetMapping("/synthetics")
    public List<SynthXact> getSynthXacts() {
        return synthXactRepository.findAll();
    }

    @GetMapping("/synthetics/open")
    public List<SynthXact> getOpenSynthXact() {
        List<String> statuses = new ArrayList<>();
        statuses.add("COMPLETED");
        statuses.add("ORPHANED");
        statuses.add("UNORPHANED");
        return synthXactRepository.findByStatusNotIn(statuses);
    }

    @PostMapping("/synthetic/start")
    public Map<String, Object> saveSynthXact(@RequestBody SynthXact xact) {
        xact.start();
        synthXactRepository.save(xact);
        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("id", xact.getID());
        return responseMap;
    }

    @GetMapping("/synthetic/end/normal/{id}")
    public Optional<SynthXact> endNormal(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().endSuccess();
        //TODO: Add null get
        synthXactRepository.save(findMe.get());
        return findMe;
    }

    @GetMapping("/synthetic/end/orphan/{id}")
    public Optional<SynthXact> orphan(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateOrphaned();
        synthXactRepository.save(findMe.get());
        return findMe;
    }

    @GetMapping("/synthetic/end/unorphaned/{id}")
    public Optional<SynthXact> unOrphan(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateAfterOrphaned();
        synthXactRepository.save(findMe.get());
        return findMe;
    }

    @GetMapping("/synthetic/late/{id}")
    public Optional<SynthXact> late(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateLate();
        synthXactRepository.save(findMe.get());
        return findMe;
    }

}
