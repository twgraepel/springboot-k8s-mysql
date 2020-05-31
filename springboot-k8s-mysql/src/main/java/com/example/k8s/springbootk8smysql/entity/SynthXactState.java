package com.example.k8s.springbootk8smysql.entity;

public enum SynthXactState {
    INITIATE,
    RUNNING,
    LATE,
    ABANDON,
    ORPHAN,
    UNORPHAN,
    COMPLETE
}
