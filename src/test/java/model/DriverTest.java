package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @Test
    void driverCaravanAssociation() {
        Driver driver = new Driver();
        Caravan caravan = new Caravan();
        driver.setCaravan(caravan);

        assertEquals(caravan.getDriver(), driver);
        Caravan caravan1 = new Caravan();
        driver.setCaravan(caravan1);
        assertNull(caravan.getDriver());
    }
}