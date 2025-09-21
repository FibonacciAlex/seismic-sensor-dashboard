package com.seismic.dashboard.service;

import com.seismic.dashboard.model.Sensor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * When the service start, load sensors data from file and store them in memory for later usage.
 */
@Service
public class SensorService {

    private static final Logger log = LoggerFactory.getLogger(SensorService.class);

    private final Map<String, Sensor> sensors = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public SensorService() {
    }

    @PostConstruct
    public void init() {
        loadSensorsFromCsv();
    }
    private void loadSensorsFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/sensor-list.csv"))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                String[] values = line.split(",");
                if (values.length >= 5) {
                    String id = values[0].trim();
                    double latitude = Double.parseDouble(values[1].trim());
                    double longitude = Double.parseDouble(values[2].trim());
                    LocalDateTime lastUpdated = LocalDateTime.parse(values[3].trim(), formatter);
                    boolean maintenanceMode = Boolean.parseBoolean(values[4].trim());
                    
                    Sensor sensor = new Sensor(id, latitude, longitude, lastUpdated, maintenanceMode);
                    sensors.put(id, sensor);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load sensors from CSV file", e);
        }
    }

    public List<Sensor> getAllSensors() {
        return new ArrayList<>(sensors.values());
    }

    public Sensor getSensorById(String id) {
        return sensors.get(id);
    }

    public boolean toggleMaintenanceMode(String id) {
        Sensor sensor = sensors.get(id);
        if(sensor == null){
            log.info("Can not find target sensor, id:{}", id);
            return false;
        }
        sensor.setMaintenanceMode(!sensor.isMaintenanceMode());
        log.info("Change sensor model success, id:{}, current model:{}", id, sensor.isMaintenanceMode());
        return sensor.isMaintenanceMode();
    }
}


