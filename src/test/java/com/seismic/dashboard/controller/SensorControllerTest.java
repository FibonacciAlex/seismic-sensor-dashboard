package com.seismic.dashboard.controller;

import com.seismic.dashboard.model.Result;
import com.seismic.dashboard.model.Sensor;
import com.seismic.dashboard.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Use Mockito
@ExtendWith(MockitoExtension.class)
class SensorControllerTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController sensorController;

    private List<Sensor> testSensors;

    @BeforeEach
    void setUp() {
        Sensor testSensor1 = new Sensor("TEST1", -43.902444, 171.747563,
                LocalDateTime.parse("2024-06-26T21:00:00"), true);
        Sensor testSensor2 = new Sensor("TEST2", -43.535929, 172.627523,
                LocalDateTime.parse("2024-08-16T21:00:00"), false);
        testSensors = Arrays.asList(testSensor1, testSensor2);
    }

    @Test
    void testGetAllSensors_Success() {
        // Arrange
        when(sensorService.getAllSensors()).thenReturn(testSensors);

        // Act
        Result<List<Sensor>> response = sensorController.getAllSensors();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("TEST1", response.getData().get(0).getId());
        assertEquals("TEST2", response.getData().get(1).getId());

        verify(sensorService, times(1)).getAllSensors();
    }

    @Test
    void testGetAllSensors_EmptyList() {
        // Arrange
        when(sensorService.getAllSensors()).thenReturn(Arrays.asList());

        // Act
        Result<List<Sensor>> response = sensorController.getAllSensors();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());

        verify(sensorService, times(1)).getAllSensors();
    }

    @Test
    void testToggleMaintenanceMode_Success() {
        // Arrange
        String sensorId = "TEST1";
        when(sensorService.toggleMaintenanceMode(sensorId)).thenReturn(false);

        // Act
        Result<Boolean> response = sensorController.toggleMaintenanceMode(sensorId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData());

        verify(sensorService, times(1)).toggleMaintenanceMode(sensorId);
    }

    @Test
    void testToggleMaintenanceMode_NotFound() {
        // Arrange
        String sensorId = "NONEXISTENT";
        when(sensorService.toggleMaintenanceMode(sensorId)).thenReturn(false);

        // Act
        Result<Boolean> response = sensorController.toggleMaintenanceMode(sensorId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData());

        verify(sensorService, times(1)).toggleMaintenanceMode(sensorId);
    }

    @Test
    void testToggleMaintenanceMode_EnableMaintenance() {
        // Arrange
        String sensorId = "TEST2";
        when(sensorService.toggleMaintenanceMode(sensorId)).thenReturn(true);

        // Act
        Result<Boolean> response = sensorController.toggleMaintenanceMode(sensorId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertTrue(response.getData());

        verify(sensorService, times(1)).toggleMaintenanceMode(sensorId);
    }

    @Test
    void testToggleMaintenanceMode_NullSensorId() {
        // Arrange
        String sensorId = null;
        when(sensorService.toggleMaintenanceMode(sensorId)).thenReturn(false);

        // Act
        Result<Boolean> response = sensorController.toggleMaintenanceMode(sensorId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData());

        verify(sensorService, times(1)).toggleMaintenanceMode(sensorId);
    }

    @Test
    void testToggleMaintenanceMode_EmptySensorId() {
        // Arrange
        String sensorId = "";
        when(sensorService.toggleMaintenanceMode(sensorId)).thenReturn(false);

        // Act
        Result<Boolean> response = sensorController.toggleMaintenanceMode(sensorId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData());

        verify(sensorService, times(1)).toggleMaintenanceMode(sensorId);
    }
}