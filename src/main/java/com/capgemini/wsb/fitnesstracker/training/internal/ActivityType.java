package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enumeration representing different types of activities.
 */
// TODO : JavaDoc
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
