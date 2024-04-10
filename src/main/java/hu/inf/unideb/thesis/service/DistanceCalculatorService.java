package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Location;

import java.util.List;

public interface DistanceCalculatorService {

    double calculateDistance(double lat1, double lon1, double lat2, double lon2);

    List<Institution> getInstitutionsByDistance(Location location, double maxDistance);
}
