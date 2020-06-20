package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuarterTest {

    @Test
    void quarterCoffinAssociation() throws Exception {
        Cemetery cemetery = new Cemetery("a", 10.0);
        Quarter quarter = Quarter.createQuarter(cemetery, "a");
        Coffin coffin = new StandardCoffin();
        quarter.setCoffin(coffin);

        assertEquals(coffin.getQuarter(), quarter);

        Quarter quarter1 = Quarter.createQuarter(cemetery, "b");
        Coffin coffin1 = new PremiumCoffin();
        coffin1.setQuarter(quarter1);

        assertEquals(quarter1.getCoffin(), coffin1);
    }

    @Test
    void quarterCoffinAssociation_changeQuarter() throws Exception {
        Cemetery cemetery = new Cemetery("fff", 40.3);
        Quarter quarter = Quarter.createQuarter(cemetery, "c");
        Coffin coffin = new StandardCoffin();
        Coffin coffin1 = new PremiumCoffin();
        quarter.setCoffin(coffin);
        quarter.setCoffin(coffin1);

        assertNull(coffin.getQuarter());
    }

    @Test
    void quarterCemeteryComposition() throws Exception {
        Cemetery cemetery = new Cemetery("ggg", 55.5);
        Quarter quarter = Quarter.createQuarter(cemetery, "a");
        assertTrue(cemetery.getQuarters().contains(quarter));
    }
}