package com.example.k8s.springbootk8smysql.controller;

import com.example.k8s.springbootk8smysql.entity.SynthXact;
import com.example.k8s.springbootk8smysql.repository.SynthXactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SynthXactController {

    @Autowired
    private SynthXactRepository synthXactRepository;

    @GetMapping("/synthetics")
    public List<SynthXact> getSynthXacts() {
        return synthXactRepository.findAll();
    }

    @PostMapping("/synthetic/start")
    public String saveSynthXact(@RequestBody SynthXact xact) {
        xact.start();
        synthXactRepository.save(xact);
        return "Synthetic Transaction added successfully::" ; //+xact.getID();
    }

    @GetMapping("/synthetic/end/normal/{id}")
    public Optional<SynthXact> endNormal(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().endSuccess();
        return findMe;
    }

    @GetMapping("/synthetic/end/orphan/{id}")
    public Optional<SynthXact> orphan(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateOrphaned();
        return findMe;
    }

    @GetMapping("/synthetic/end/unorphaned/{id}")
    public Optional<SynthXact> unOrphan(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateAfterOrphaned();
        return findMe;
    }

    @GetMapping("/synthetic/late/{id}")
    public Optional<SynthXact> late(@PathVariable UUID id) {
        Optional<SynthXact> findMe = synthXactRepository.findById(id);
        findMe.get().updateLate();
        return findMe;
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "This is the about string!";
    }

}
