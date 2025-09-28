package com.example.backend.model.Projects;

public enum RequestType {
            INVITE,         //Admin till user
            REQUEST,        //User till Admin
            ACCEPTED,
            DECLINED,      // om user/admin nekar andra part, behåller vi dem som nekad i db
            REVOKED
}
