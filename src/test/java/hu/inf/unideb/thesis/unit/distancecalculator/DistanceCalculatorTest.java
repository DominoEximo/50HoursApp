package hu.inf.unideb.thesis.unit.distancecalculator;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.Location;
import hu.inf.unideb.thesis.service.DistanceCalculatorService;
import hu.inf.unideb.thesis.service.InstitutionService;
import jakarta.transaction.Transactional;
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
public class DistanceCalculatorTest {

    @Autowired
    DistanceCalculatorService distanceCalculatorService;

    @Autowired
    InstitutionService institutionService;

    @Test
    public void testCalculateDistance(){
        Double testData = distanceCalculatorService.calculateDistance(38.8976,-77.0366,39.9496,-75.1503);

        Assertions.assertTrue(testData > 199);
    }

    @Test
    public void testGetInstitutionsByDistance(){
        Location testUserLocation = new Location();
        Location testLocation = new Location();
        Location testLocation2 = new Location();
        Institution testInstitution = new Institution();
        Institution testInstitution2 = new Institution();

        testUserLocation.setLat(38.8976);
        testUserLocation.setLon(-77.0366);

        testLocation.setLat(39.9496);
        testLocation.setLon(-75.1503);

        testLocation2.setLat(47.4979);
        testLocation2.setLon(19.0402);

        testInstitution.setLocation(testLocation);
        testInstitution2.setLocation(testLocation2);

        institutionService.save(testInstitution);
        institutionService.save(testInstitution2);

        Assertions.assertEquals(1,distanceCalculatorService.getInstitutionsByDistance(testUserLocation,200).size());
    }
}
