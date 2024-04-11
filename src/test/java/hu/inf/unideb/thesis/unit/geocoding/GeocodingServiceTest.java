package hu.inf.unideb.thesis.unit.geocoding;

import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.service.GeocodingService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("unit")
public class GeocodingServiceTest {

    @Autowired
    GeocodingService geocodingService;

    private double delta = 0.0001;
    @Test
    public void testGeocodeAddressCorrectAddress(){

        Location location = geocodingService.geocodeAddress("Hungary");



        Assertions.assertEquals(47.1817585,location.getLat(),delta);
        Assertions.assertEquals(19.5060937,location.getLon(),delta);

    }

    @Test
    public void testGeocodeAddressIncorrectAddress(){
        Location location = geocodingService.geocodeAddress("Hungaryy");

        Assertions.assertEquals(0,location.getLat(),delta);
        Assertions.assertEquals(0,location.getLon(),delta);
    }
}
