package com.seismic.dashboard.controller;

import com.seismic.dashboard.model.Result;
import com.seismic.dashboard.model.Sensor;
import com.seismic.dashboard.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sensor operation API
 */
@RestController
@RequestMapping("/api")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    /**
     * TODO Currently just return all data, if the amount is very large,
     * we should use Pagination to return page by page in practice.
     * @return sensor list
     */
    @GetMapping("/sensors")
    public Result<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return Result.success(sensors);
    }

    /**
     * Operation by ID
     * @param id sensor id
     * @return new mode
     */
    @PostMapping("/sensors/{id}/maintenance")
    public Result<Boolean> toggleMaintenanceMode(@PathVariable String id) {
        boolean newMaintenanceMode = sensorService.toggleMaintenanceMode(id);
        return Result.success(newMaintenanceMode);
    }
}


