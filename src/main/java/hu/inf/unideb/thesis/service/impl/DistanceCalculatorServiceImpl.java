package hu.inf.unideb.thesis.service.impl;

import hu.inf.unideb.thesis.dao.InstitutionDAO;
import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.service.DistanceCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for calculating distance and filtering institutions.
 */
@Service
public class DistanceCalculatorServiceImpl implements DistanceCalculatorService {

    @Autowired
    InstitutionDAO institutionDAO;

    /***
     * Calculates the distance between 2 coordinates.
     * @param lat1 Latitude of first location.
     * @param lon1 Longitude of first location.
     * @param lat2 Latitude of second location.
     * @param lon2 Longitude of second location.
     * @return the distance between the 2 locations.
     */
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        // Radius of the Earth in kilometers
        final double R = 6371.0;

        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the differences in latitude and longitude
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Calculate the distance using the Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    /***
     * Retrieves a filtered list of institutions based on a given distance from a user's location.
     * @param userLocation The location of a user.
     * @param maxDistance The distance for calculating.
     * @return a filtered list of institutions.
     */
    @Override
    public List<Institution> getInstitutionsByDistance(Location userLocation, double maxDistance) {

        List<Institution> institutions = institutionDAO.getAll();
        List<Institution> nearbyInstitutions = new ArrayList<>();

        for (Institution institution : institutions){
            double distance = calculateDistance(userLocation.getLat(), userLocation.getLon(), institution.getLocation().getLat(), institution.getLocation().getLon());
            if (distance <= maxDistance){
                nearbyInstitutions.add(institution);
            }
        }

        return nearbyInstitutions;
    }
}
