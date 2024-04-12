package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.service.GeocodingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service layer for converting an address to coordinates for calculations.
 */
@Service
public class GeocodingServiceImpl implements GeocodingService {

    /***
     * Creates a location from a string address using open maps API.
     * @param address the address of user or institution.
     * @return a Location object with coordinates.
     */
    @Override
    public Location geocodeAddress(String address) {
        String url = "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json";
        RestTemplate restTemplate = new RestTemplate();
        Location location = new Location();
        try {

            Location[] locations = restTemplate.getForObject(url, Location[].class);


            if (locations != null && locations.length > 0) {
                location = locations[0];
            } else {
                System.out.println("No location found for the provided address.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while geocoding address: " + e.getMessage());
        }

        return location;
    }
}
