let sensors = [];
let map;
let markers = [];

// Load sensors on page load
document.addEventListener('DOMContentLoaded', function() {
    loadSensors();
});

async function loadSensors() {
    try {
        const response = await fetch('/api/sensors');
        if (!response.ok) {
            alert('Failed to load sensors');
            return;
        }
        const result = await response.json();
        if(result.code !== 200){
            alert(result.message);
            return;
        }
        sensors = result.data;
        displaySensors();
    } catch (error) {
        console.error('Error loading sensors:', error);
        document.getElementById('loading').style.display = 'none';
        document.getElementById('error').style.display = 'block';
        document.getElementById('error').textContent = 'Error loading sensors: ' + error.message;
    }
}

//display table
function displaySensors() {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('sensorTable').style.display = 'table';

    const tbody = document.getElementById('sensorTableBody');
    tbody.innerHTML = '';

    sensors.forEach(sensor => {
        const row = document.createElement('tr');
        row.innerHTML = `
                    <td>${sensor.id}</td>
                    <td>${sensor.latitude.toFixed(6)}</td>
                    <td>${sensor.longitude.toFixed(6)}</td>
                    <td>${formatTimeAgo(sensor.lastUpdated)}</td>
                    <td>${sensor.maintenanceMode ? 'Yes' : 'No'}</td>
                    <td>
                        <button class="maintenance-toggle ${sensor.maintenanceMode ? 'enabled' : 'disabled'}"
                                onclick="toggleMaintenance('${sensor.id}', this, 'table')">
                            ${sensor.maintenanceMode ? 'Disable' : 'Enable'}
                        </button>
                    </td>
                `;
        tbody.appendChild(row);
    });
}

async function toggleMaintenance(sensorId, button, type) {
    try {
        const response = await fetch(`/api/sensors/${sensorId}/maintenance`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            alert('Failed to toggle maintenance mode');
            return;
        }

        const result = await response.json();
        if(result.code !== 200){
            alert(result.message);
            return;
        }
        const newMaintenanceMode = result.data;
        // Update the sensor in our local array
        const sensor = sensors.find(s => s.id === sensorId);
        if (sensor) {
            sensor.maintenanceMode = newMaintenanceMode;
        }

        // Update the button appearance
        if (newMaintenanceMode) {
            button.className = 'maintenance-toggle enabled';
            button.textContent = 'Disable';
        } else {
            button.className = 'maintenance-toggle disabled';
            button.textContent = 'Enable';
        }
        if(type === 'table'){
            button.parentElement.previousElementSibling.textContent = newMaintenanceMode ? 'Yes' : 'No';
        }

        // Update map markers if map view is active
        if (type === 'map') {
            updateMapMarkers();
        }

    } catch (error) {
        console.error('Error toggling maintenance mode:', error);
        alert('Error toggling maintenance mode: ' + error.message);
    }
}

function formatTimeAgo(timestamp) {
    const now = new Date();
    const sensorTime = new Date(timestamp);
    const diffMs = now - sensorTime;
    const diffMinutes = Math.floor(diffMs / (1000 * 60));
    const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));

    if(diffMinutes < 0){
        console.warn(`Found incorrect data, timestamp:  ${timestamp}`)
        return '0 minute ago';
    }
    if (diffMinutes < 60) {
        return `${diffMinutes} minute${diffMinutes !== 1 ? 's' : ''} ago`;
    } else if (diffHours < 24) {
        return `${diffHours} hour${diffHours !== 1 ? 's' : ''} ago`;
    } else {
        return `${diffDays} day${diffDays !== 1 ? 's' : ''} ago`;
    }
}

function showTableView() {
    let tableViewBtn = document.getElementById('tableViewBtn');

    if(tableViewBtn.classList.contains('active')){
        //currently on table view, just return
        return;
    }
    document.getElementById('tableView').style.display = 'block';
    document.getElementById('mapView').style.display = 'none';
    tableViewBtn.classList.add('active');
    document.getElementById('mapViewBtn').classList.remove('active');
    //refresh table
    displaySensors();
}

function showMapView() {
    let mapViewBtn = document.getElementById('mapViewBtn');
    if(mapViewBtn.classList.contains('active')){
        //currently on map view, just return
        return;
    }
    document.getElementById('tableView').style.display = 'none';
    document.getElementById('mapView').style.display = 'block';
    document.getElementById('tableViewBtn').classList.remove('active');
    mapViewBtn.classList.add('active');

    if (!map) {
        initMap();
    }else{
        //refresh marker
        updateMapMarkers();
    }
}

//use leaflet to display
function initMap() {
    // Initialize a simple map using OpenStreetMap
    map = L.map('map').setView([-43.0, 172.0], 6); // Center on New Zealand

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    updateMapMarkers();
}

function updateMapMarkers() {
    // Clear existing markers
    markers.forEach(marker => map.removeLayer(marker));
    markers = [];

    sensors.forEach(sensor => {
        const marker = L.marker([sensor.latitude, sensor.longitude]).addTo(map);

        const popupContent = `
                    <div class="sensor-info">
                        <h4>Sensor ${sensor.id}</h4>
                        <p><strong>Location:</strong> ${sensor.latitude.toFixed(6)}, ${sensor.longitude.toFixed(6)}</p>
                        <p><strong>Last Updated:</strong> ${formatTimeAgo(sensor.lastUpdated)}</p>
                        <p><strong>Maintenance Mode:</strong> ${sensor.maintenanceMode ? 'Yes' : 'No'}</p>
                        <button class="maintenance-toggle ${sensor.maintenanceMode ? 'enabled' : 'disabled'}"
                                onclick="toggleMaintenance('${sensor.id}', this, 'map')">
                            ${sensor.maintenanceMode ? 'Disable' : 'Enable'}
                        </button>
                    </div>
                `;

        marker.bindPopup(popupContent);

        // Color code markers based on maintenance mode
        if (sensor.maintenanceMode) {
            marker.setIcon(L.divIcon({
                className: 'maintenance-marker',
                html: '<div style="background-color: red; width: 10px; height: 10px; border-radius: 50%; border: 2px solid white;"></div>',
                iconSize: [14, 14]
            }));
        } else {
            marker.setIcon(L.divIcon({
                className: 'normal-marker',
                html: '<div style="background-color: green; width: 10px; height: 10px; border-radius: 50%; border: 2px solid white;"></div>',
                iconSize: [14, 14]
            }));
        }

        markers.push(marker);
    });
}