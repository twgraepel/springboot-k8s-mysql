package com.example.k8s.springbootk8smysql.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "spring_synthxact")
public class SynthXact {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID ID;
    private String synthType;
    private String processID;
    private String instanceID;
    private String status;
    @Temporal(TemporalType.TIMESTAMP) private Date startTS;
    @Temporal(TemporalType.TIMESTAMP) private Date endTS;
    @Temporal(TemporalType.TIMESTAMP) private Date lastTS;

    public void start() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        startTS = new Date();
        lastTS = startTS;
        status = "INITIATED";
    }


    public void endSuccess() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        endTS = new Date();
        lastTS = endTS;
        status = "COMPLETED";
    }

    public void updateRunning() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        lastTS = new Date();
        status = "RUNNING";
    }

    public void updateLate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        lastTS = new Date();
        status = "LATE";
    }
    public void updateOrphaned() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        lastTS = new Date();
        endTS = lastTS;
        status = "ORPHANED";
    }

    public void updateAfterOrphaned() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        lastTS = new Date();
        endTS = lastTS;
        status = "UPD_AFTER_ORPHANED";
    }

}