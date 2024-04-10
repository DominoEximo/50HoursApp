package hu.inf.unideb.thesis.service;

import hu.inf.unideb.thesis.entity.Location;

public interface GeocodingService {

    Location geocodeAddress(String address);
}
