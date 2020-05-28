package com.example.k8s.springbootk8smysql.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    public UUID getID() { return ID; }

    @lombok.NonNull
    private String synthType;

    private String processID;
    private String instanceID;

    //@Column(updatable = false)
    private String status = "INITIATED";
    private LocalDateTime startTS;
    private LocalDateTime endTS;
    private LocalDateTime lastTS;

    @Transient private Long ageInSeconds;

    public Long getAgeInSeconds() {
        Long timeInSeconds = 0L;
        LocalDateTime localDateEnd = LocalDateTime.now();
        if (endTS != null) {
            localDateEnd = endTS;
        }
        timeInSeconds = (long) ChronoUnit.SECONDS.between( startTS, localDateEnd);
        return timeInSeconds;
    }

    public void start() {
        startTS = LocalDateTime.now();
        lastTS = startTS;
    }

    public void endSuccess() {
        endTS = LocalDateTime.now();
        lastTS = endTS;
        status = "COMPLETED";
    }

    public void updateRunning() {
        lastTS = LocalDateTime.now();;
        status = "RUNNING";
    }

    public void updateLate() {
        lastTS = LocalDateTime.now();
        status = "LATE";
    }

    public void updateOrphaned() {
        lastTS = LocalDateTime.now();
        endTS = lastTS;
        status = "ORPHANED";
    }

    public void updateAfterOrphaned() {
        lastTS = LocalDateTime.now();
        endTS = lastTS;
        status = "UNORPHANED";
    }
}