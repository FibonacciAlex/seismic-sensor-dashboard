package com.seismic.dashboard.service;

import com.seismic.dashboard.model.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Use Springboot test
@SpringBootTest
public class SensorServiceWithTestConfigTest {

    @Autowired
    private SensorService sensorService;

    @TestConfiguration
    static class TestConfig {

        //init bean here to trigger file loading logic
        @Bean
        @Primary
        public SensorService testSensorService() {
            return new SensorService();
        }

    }

    @Test
    public void testGetAllSensors() {
        var sensors = sensorService.getAllSensors();
        assertEquals(28, sensors.size());
    }

    @Test
    public void testGetSensorById() {
        Sensor sensor = sensorService.getSensorById("GORS");
        assertNotNull(sensor);
        assertEquals("GORS", sensor.getId());
    }
}