package com.example.k8s.springbootk8smysql.entity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "spring_synthxact")
@Entity
public class SynthXact {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID ID;
    public UUID getID() { return ID; }

    @lombok.NonNull
    private String synthType;

    @Column(columnDefinition = "TEXT")
    private String keyToCheck;
    @Column(columnDefinition = "TEXT")
    private String targertToCheck;

    @Enumerated(EnumType.STRING)
    private SynthXactState synState;

    private String assignedTo;
    private String processID;
    private String instanceID;
    private Integer hops = 0;

    //@Column(updatable = false)
    private String status = "INITIATE";
    private LocalDateTime startTS;
    private LocalDateTime endTS;
    private LocalDateTime lastTS;

    @Transient private Long ageInSeconds;

    private void incrementHops() { hops++; }

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
        status = "INITIATE";
        hops = 0;
    }

    public void endComplete() {
        endTS = LocalDateTime.now();
        lastTS = endTS;
        status = "COMPLETE";
        incrementHops();
    }
    public void endAbandon() {
        endTS = LocalDateTime.now();
        lastTS = endTS;
        status = "ABANDON";
        incrementHops();
    }

    public void updateRunning() {
        lastTS = LocalDateTime.now();;
        status = "RUNNING";
        incrementHops();
    }

    public void updateLate() {
        lastTS = LocalDateTime.now();
        status = "LATE";
        incrementHops();
    }

    public void updateOrphan() {
        lastTS = LocalDateTime.now();
        status = "ORPHAN";
        incrementHops();
    }

    public void updateUnOrphan() {
        lastTS = LocalDateTime.now();
        status = "UNORPHAN";
        incrementHops();
    }
}