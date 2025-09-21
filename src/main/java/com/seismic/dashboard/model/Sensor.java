package com.seismic.dashboard.model;

import java.time.LocalDateTime;

public class Sensor {
    private String id;
    private double latitude;
    private double longitude;
    private LocalDateTime lastUpdated;
    private boolean maintenanceMode;

    public Sensor() {}

    public Sensor(String id, double latitude, double longitude, LocalDateTime lastUpdated, boolean maintenanceMode) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
        this.maintenanceMode = maintenanceMode;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lastUpdated=" + lastUpdated +
                ", maintenanceMode=" + maintenanceMode +
                '}';
    }
}


