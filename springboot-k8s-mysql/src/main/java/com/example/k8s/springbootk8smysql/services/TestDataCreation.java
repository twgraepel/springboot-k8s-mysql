package com.example.k8s.springbootk8smysql.services;

import com.example.k8s.springbootk8smysql.entity.SynthXact;
import com.example.k8s.springbootk8smysql.repository.SynthXactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
//@Service
public class TestDataCreation {
    @Autowired
    private SynthXactRepository synRepo;
    private HashMap<String, HashMap<String, String>> allowedRoutes = new HashMap<String, HashMap<String, String>>();

    @Autowired
    private SynthXactService synService;
    private String getRandomProgression(String status){

        String tempString = null;
        if (allowedRoutes.containsKey(status)) {
            HashMap<String, String> subHM = allowedRoutes.get(status);
            List<String> result = subHM.entrySet()
                    .stream()
                    .filter(x-> "Allowed".equals(x.getValue()))
                    .map(x->x.getKey())
                    .collect(Collectors.toList());
            log.info("My New List {}", result);
            if (result.size() > 0){
                Random rand =  new Random();
                tempString = result.get(rand.nextInt(result.size()));
            }
        }
        return tempString;
    }

    public void progressOneID(UUID id, String status){
        Optional<SynthXact> mySynth = synService.getSynthXactByID(id);
        if (mySynth.isEmpty()){
            log.info("{} was not found to perform progression", id);
            return;
        }
        SynthXact operateSynth = mySynth.get();
        String toStatus = status;
        String fromStatus = mySynth.get().getStatus();
        if (toStatus.equals("RANDOM") ) {
            toStatus = getRandomProgression(fromStatus);
        }
        if (allowedRoutes.containsKey(fromStatus) ) {
            if (allowedRoutes.get(fromStatus).containsKey(toStatus)){
                if (allowedRoutes.get(fromStatus).get(toStatus).equals("Allowed")){
                    switch (toStatus.toLowerCase()) {
                        case "running":
                            operateSynth.updateRunning();
                            break;
                        case "late":
                            operateSynth.updateLate();
                            break;
                        case "orphan":
                            operateSynth.updateOrphan();
                            break;
                        case "unorphan":
                            operateSynth.updateUnOrphan();
                            break;
                        case "complete":
                            operateSynth.endComplete();
                            break;
                        case "abandon":
                            operateSynth.endAbandon();
                            break;
                        default:
                            log.warn("Never should have gotten in here");
                    }
                    synRepo.save(operateSynth);
                }
            }
        }
    }

    @Scheduled(fixedDelay=2000 )
    public void progressFirstID() {
        allowedRoutes = synService.getAllowedRoutes();
        SynthXact firstID = synRepo.findFirstByStatusIn(Arrays.asList("INITIATE","ORPHAN","RUNNING", "LATE", "UNORPHAN"));
        if (firstID != null) {
            log.info("ID Chosen for progression is {}", firstID.getID());
            this.progressOneID(firstID.getID(), "RANDOM");
        } else { log.info("No valid choice available for progression");}
    }

    @Scheduled(fixedDelay=10000)
    public void RunMe() {
        if (synService.countByStatusIn(Arrays.asList("INITIATE","RUNNING", "LATE", "UNORPHAN"))  <= 2 ) {
            List<String> givenList = Arrays.asList("XXX", "YYY", "ABC", "DEF", "GHI", "X12", "DOG", "CAT", "OFF");
            Random rand =  new Random();
            String randomElement = givenList.get(rand.nextInt(givenList.size()));
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

            //List<String> suffix = Arrays.asList("_id", "_pk");
            //List<String> prefix = Arrays.asList("cust", "prod");
            List<String> suffix = Arrays.asList("_id", "_pk", "_key", "_cd", "CD", "PK", "ID", "GUID","_guid", "FK", "_fk");
            List<String> prefix = Arrays.asList("precinct", "crime", "incident","cust", "prod", "emp", "order", "ship", "vendor", "pack", "component", "badge");

            String randomKey = prefix.get(rand.nextInt(prefix.size())) + suffix.get(rand.nextInt(suffix.size()));
            String randomKey2 = prefix.get(rand.nextInt(prefix.size())) + suffix.get(rand.nextInt(suffix.size()));
            String randomVal = givenList.get(rand.nextInt(givenList.size()));
            randomVal += AlphaNumericString.charAt(rand.nextInt(givenList.size()));
            randomVal = AlphaNumericString.charAt(rand.nextInt(givenList.size())) + randomVal;

            String subRandomKey = String.format("{\"%s\":\"%d\",\"%s\":\"%s\"}",randomKey, rand.nextInt(90000), randomKey2, randomVal );

            if (randomKey.equals(randomKey2)) {
                subRandomKey = String.format("{\"%s\":%d}",randomKey, rand.nextInt(90000) );
            }

            SynthXact mySynth = SynthXact.builder()
                    .synthType("DEFAULT")
                    .status("INITIATE")
                    .keyToCheck(subRandomKey)
                    .processID(randomElement)
                    .build();
            mySynth.start();
            synService.saveSynthXact(mySynth);
        }
    }

}
