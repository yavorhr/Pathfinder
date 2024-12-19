document.addEventListener('DOMContentLoaded', function () {
    var mapContainer = document.getElementById('map');

    if (mapContainer) {
        // 1. Initializing the map with zoom level 15
        var map = L.map('map').setView([0, 0], 14);

        // Add title layer
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap'
        })
            .addTo(map);

        // Load GPX data if available
        if (gpxData) {
            var blob = new Blob([gpxData], { type: 'application/gpx+xml' });
            var url = URL.createObjectURL(blob);

            new L.GPX(url, {
                async: true,
                marker_options: {
                    startIconUrl: 'https://unpkg.com/leaflet-gpx@1.6.1/images/pin-icon-start.png',
                    endIconUrl: 'https://unpkg.com/leaflet-gpx@1.6.1/images/pin-icon-end.png',
                    shadowUrl: 'https://unpkg.com/leaflet-gpx@1.6.1/images/pin-shadow.png'
                }
            }).on('loaded', function(e) {
                map.fitBounds(e.target.getBounds()); // Adjust the map view to fit GPX data
            })
                .addTo(map);
        } else {
            console.error("No GPX data found."); // Handle no GPX data
        }
    } else {
        console.error("Map container not found."); // Log if container is missing
    }
});